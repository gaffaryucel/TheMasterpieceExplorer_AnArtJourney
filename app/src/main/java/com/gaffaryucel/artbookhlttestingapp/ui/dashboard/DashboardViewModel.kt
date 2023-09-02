package com.gaffaryucel.artbookhlttestingapp.ui.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.FirebaseArtistModel
import com.gaffaryucel.artbookhlttestingapp.model.UserModel
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardViewModel : ViewModel() {
    private var loadMessage = MutableLiveData<Resource<UserModel>>()
    val message : LiveData<Resource<UserModel>>
        get() = loadMessage

    private val userDataMld = MutableLiveData<UserModel>()
    val userData : LiveData<UserModel>
        get() = userDataMld

    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance(Util.DATABASE_URL)
    private val myRef = database.reference
    init {
        getUserData()
    }
    private fun getUserData() = viewModelScope.launch {
        val likedArtistsRef = myRef.child("users").orderByKey()
            .equalTo(userId)

        // Listener ile verileri alın ve işleyin
        likedArtistsRef.addListenerForSingleValueEvent(object : ValueEventListener {
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
                    } else {
                        println("Beğenilen sanatçı bulunamadı.")
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Veriler alınırken hata oluştu: ${databaseError.message}")
            }
        })
    }


}