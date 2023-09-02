package com.gaffaryucel.artbookhlttestingapp.ui.notifications

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.adapter.HomePageAdapter
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.ArtistDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoImpl
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repo : WikiArtApiRepoInterface
): ViewModel() {

    private val disposable  = CompositeDisposable()
    private var insertArtMsg = MutableLiveData<Resource<Artist>>()
    val message : LiveData<Resource<Artist>>
        get() = insertArtMsg

    private val artistList = MutableLiveData<List<Artist>>()
    val artistListLiveData : LiveData<List<Artist>>
        get() = artistList

    private val homePageItemsMLD = MutableLiveData<List<HomePageModel>>()
    val homePageItems : LiveData<List<HomePageModel>>
        get() = homePageItemsMLD

    init {
        getPopularArtist()
        getMostViewedArts()
    }

    @SuppressLint("CheckResult")
    private fun getPopularArtist() = viewModelScope.launch {
        insertArtMsg.postValue(Resource.loading(null))

        val single = repo.getPopularArtists()

        disposable.add(single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                insertArtMsg.value = Resource.success(null)
                artistList.value = response
            }, { error ->
                insertArtMsg.value = Resource.error("İstek başarısız: ${error.localizedMessage}",null)
            })
        )
    }
    private fun getMostViewedArts()= viewModelScope.launch {
        val single = repo.getMostViewedArts()

        disposable.add(single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                homePageItemsMLD.value = response
            }, { error ->
                insertArtMsg.value = Resource.error("İstek başarısız: ${error.localizedMessage}",null)
            })
        )
    }
}

//en/App/Painting/PaintingsByArtist?artistUrl=aleksandra-ekster&json=2

//https://www.wikiart.org/
/*
https://www.wikiart.org/en/app/api/popularartists?json=1
https://www.wikiart.org/en/App/Painting/MostViewedPaintings?randomSeed=123&json=2&inPublicDomain=true
https://www.wikiart.org/en/App/Painting/PaintingsByArtist?artistUrl=aleksandra-ekster&json=2
App/Painting/PaintingsByArtist?json=2&artistUrl={artistUrl}
 */

