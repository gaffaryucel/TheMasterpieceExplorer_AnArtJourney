package com.gaffaryucel.artbookhlttestingapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.model.UserModel
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class ArtListViewModel  @Inject constructor(
    private val repo : ArtRepoInterface
): ViewModel() {
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance(Util.DATABASE_URL)
    private val myRef = database.reference
    val arts = repo.observeArts()

    private var messageMld = MutableLiveData<Resource<ArtModel>>()
    val message : LiveData<Resource<ArtModel>>
        get() = messageMld

    private val userDataMld = MutableLiveData<UserModel>()
    val userData : LiveData<UserModel>
        get() = userDataMld

    private val images = MutableLiveData<Resource<PixabeyModel>>()
    val imageList : LiveData<Resource<PixabeyModel>>
        get() = images

    init {
        getUserData()
    }

    fun getUserData() = viewModelScope.launch {
        messageMld.value = Resource.loading(null)
        val userDataRef = myRef.child("users").orderByKey()
            .equalTo(userId)

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

}