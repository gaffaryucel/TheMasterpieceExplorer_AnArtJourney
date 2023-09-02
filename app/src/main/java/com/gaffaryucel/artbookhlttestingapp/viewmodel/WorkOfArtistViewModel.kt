package com.gaffaryucel.artbookhlttestingapp.viewmodel

import android.annotation.SuppressLint
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gaffaryucel.artbookhlttestingapp.adapter.HomePageAdapter
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.FirebaseArtistModel
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoInterface
import com.gaffaryucel.artbookhlttestingapp.repo.WorkOfArtistRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.util.Util.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkOfArtistViewModel @Inject constructor(
    private val repo : WorkOfArtistRepoInterface,
    private val wikiRepo : WikiArtApiRepoInterface
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance(DATABASE_URL)
    private val myRef = database.reference

    private val artistImage = MutableLiveData<String>()
    val artistImageLd : LiveData<String>
        get() = artistImage

    private var myresponse = MutableLiveData<Resource<Artist>>()
    val responseMessage: LiveData<Resource<Artist>>
        get() = myresponse

    private var workOfArtistMutable = MutableLiveData<List<Art>>()
    val workOfArtistLD: LiveData<List<Art>>
        get() = workOfArtistMutable


    @SuppressLint("CheckResult")
    fun getWorkOfArtist(artist: String) = viewModelScope.launch {
        myresponse.postValue(Resource.loading(null))

        val single = repo.getPaintingsByArtist(artist)
        disposable.add(
            single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response?.let {
                        myresponse.value = Resource.success(null)
                        workOfArtistMutable.value = it
                    }
                }, { error ->
                    println(error.localizedMessage)
                    myresponse.value =
                        Resource.error("İstek başarısız: ${error.localizedMessage}", null)
                })
        )
    }
    suspend fun getArtistPhoto(artist: String) = viewModelScope.launch {
            val single = wikiRepo.getArtistDetails(artist)

            disposable.add(
                single
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        response?.let {
                            artistImage.value = it.image
                        }
                    }, { error ->
                        println(error.localizedMessage)

                    })
            )
    }

    fun saveLikedArtist(artist: FirebaseArtistModel) = viewModelScope.launch{
        // Oluşturulan sanatçıyı yeni anahtarın altına ekleyin
        myRef.child("users").child(userId).child("liked_artists").child(artist.name ?: "").setValue(artist)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Sanatçı başarıyla eklendi.")
                } else {
                    println("Sanatçı eklenirken bir hata oluştu: " + task.exception)
                }
            }
    }
}
