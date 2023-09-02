package com.gaffaryucel.artbookhlttestingapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.FirebaseArtistModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel : ViewModel() {

    private var loadMessage = MutableLiveData<Resource<FirebaseArtistModel>>()
    val message : LiveData<Resource<FirebaseArtistModel>>
        get() = loadMessage

    private val artistsMld = MutableLiveData<List<FirebaseArtistModel>>()
    val artists : LiveData<List<FirebaseArtistModel>>
        get() = artistsMld

    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance(Util.DATABASE_URL)
    private val myRef = database.reference
    init {
        getSavedArtist()
    }
    private fun getSavedArtist() = viewModelScope.launch {
        val likedArtistsRef = myRef.child("users").child(userId).child("liked_artists")

        // Listener ile verileri alın ve işleyin
        likedArtistsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val artistList = ArrayList<FirebaseArtistModel>()
                runBlocking {
                    if (dataSnapshot.exists()) {
                        for (artistSnapshot in dataSnapshot.children) {
                            // Sanatçının veri modelini elde edin
                            val artist = artistSnapshot.getValue(FirebaseArtistModel::class.java)
                            if (artist!=null){
                                artistList.add(artist)
                            }
                        }
                    } else {
                        println("Beğenilen sanatçı bulunamadı.")
                    }
                    artistsMld.value = artistList
                    println("a : "+artistsMld.value)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Veriler alınırken hata oluştu: ${databaseError.message}")
            }
        })
    }
}