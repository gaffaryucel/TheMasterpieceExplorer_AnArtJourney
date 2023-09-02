package com.gaffaryucel.artbookhlttestingapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.ArtDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.repo.WorkOfArtistRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkOfArtistDetailsViewModel @Inject constructor(
    private val repo : WorkOfArtistRepoInterface,
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private var myresponse = MutableLiveData<Resource<Artist>>()
    val responseMessage: LiveData<Resource<Artist>>
        get() = myresponse

    private var workOfArtistMutable = MutableLiveData<ArtDetailsModel>()
    val workOfArtist: LiveData<ArtDetailsModel>
        get() = workOfArtistMutable


    @SuppressLint("CheckResult")
    fun getWorkOfArtist(contentid: String) = viewModelScope.launch{
        myresponse.postValue(Resource.loading(null))

        val single = repo.getArtDetails(contentid)
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
}