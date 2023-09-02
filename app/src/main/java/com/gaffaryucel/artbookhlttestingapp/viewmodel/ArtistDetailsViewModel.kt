package com.gaffaryucel.artbookhlttestingapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.ArtistDetailsModel
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoInterface
import com.gaffaryucel.artbookhlttestingapp.repo.WorkOfArtistRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ArtistDetailsViewModel @Inject constructor(
    private val repo : WikiArtApiRepoInterface
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private var myresponse = MutableLiveData<Resource<ArtistDetailsModel>>()
    val responseMessage: LiveData<Resource<ArtistDetailsModel>>
        get() = myresponse

    private var artistInfo = MutableLiveData<ArtistDetailsModel>()
    val artistData: LiveData<ArtistDetailsModel>
        get() = artistInfo

    @SuppressLint("CheckResult")
    fun getArtistInfo(artist: String) = viewModelScope.launch{

        myresponse.postValue(Resource.loading(null))

        val single = repo.getArtistDetails(artist)
        disposable.add(
            single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response?.let {
                        myresponse.value = Resource.success(null)
                        artistInfo.value = it
                    }
                }, { error ->
                    println(error.localizedMessage)
                    myresponse.value =
                        Resource.error("İstek başarısız: ${error.localizedMessage}", null)
                })
        )
    }
}