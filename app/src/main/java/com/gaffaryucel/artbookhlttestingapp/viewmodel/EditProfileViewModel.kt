package com.gaffaryucel.artbookhlttestingapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.UserModel
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EditProfileViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val userId = FirebaseAuth.getInstance().currentUser!!.uid

    private val database = FirebaseDatabase.getInstance(Util.DATABASE_URL)
    private val userDatabaseRef = database.reference.child("users")
    private val userDataRef = userDatabaseRef.orderByKey().equalTo(userId)

    private var messageMld = MutableLiveData<Resource<ArtModel>>()
    val message : LiveData<Resource<ArtModel>>
        get() = messageMld

    private val userDataMld = MutableLiveData<UserModel>()
    val userData : LiveData<UserModel>
        get() = userDataMld

    init {
        getUserData()
    }

    fun updateDataInFirebase(currentModel: UserModel,newModel: UserModel) {
        val newData = compareAndCopyUserModels(currentModel,newModel)
        messageMld.value = Resource.loading(null)
        userDatabaseRef.child(userId)
            .updateChildren(newData)
            .addOnSuccessListener {
                getUserData()
                updateEmail(newData.get("email").toString())
            }
            .addOnFailureListener { error ->
                messageMld.value = error.localizedMessage?.let { Resource.error(it,null) }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    private fun getUserData() = viewModelScope.launch {
        messageMld.value = Resource.loading(null)
        // Listener ile verileri alın ve işleyin
        userDataRef.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                runBlocking {
                    if (dataSnapshot.exists()) {
                        for (artistSnapshot in dataSnapshot.children) {
                            // Sanatçının veri modelini elde edin
                            val user = artistSnapshot.getValue(UserModel::class.java)
                            if (user!=null){
                                userDataMld.value = user
                            }
                        }
                        messageMld.value = Resource.success(null)
                    } else {
                        messageMld.value = Resource.error("Beğenilen sanatçı bulunamadı.",null)
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Veriler alınırken hata oluştu: ${databaseError.message}")
            }
        })
    }
    private fun compareAndCopyUserModels(currentInfo: UserModel, newInfo: UserModel) :  Map<String, Any> {
        val updatedData = HashMap<String, Any>()

        // isim karşılaştırması ve kopyalama
        if (!newInfo.name.isNullOrEmpty()) {
            updatedData["name"] = newInfo.name!!
        }else{
            updatedData["name"] = currentInfo.name!!
        }

        // Email karşılaştırması ve kopyalama
        if (!newInfo.email.isNullOrEmpty()) {
            updatedData["email"] = newInfo.email ?: firebaseAuth.currentUser!!.email!!
        }else{
            updatedData["email"] = currentInfo.email ?: firebaseAuth.currentUser!!.email!!
        }

        // ProfilePhoto karşılaştırması ve kopyalama
        if (!newInfo.profilePhoto.isNullOrEmpty()) {
            updatedData["profilePhoto"] = newInfo.profilePhoto ?: ""
        }else{
            updatedData["profilePhoto"] = currentInfo.profilePhoto ?: ""
        }
        return updatedData
    }
    private fun updateEmail(newEmail : String){
        println("start")
        if (firebaseAuth.currentUser != null){
            println("auth")
        }
        firebaseAuth.currentUser!!.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                println("complete")
                if (task.isSuccessful) {
                    println("success")
                    messageMld.value = Resource.success(null)
                    // E-posta başarıyla değiştirildi
                    val updatedUser = FirebaseAuth.getInstance().currentUser
                    println("Yeni e-posta: ${updatedUser?.email}")
                } else {
                    messageMld.value = Resource.error(task.exception?.localizedMessage ?: "error : try again",null)
                    println("hata : ${task.exception?.localizedMessage}")
                }
            }
    }
}