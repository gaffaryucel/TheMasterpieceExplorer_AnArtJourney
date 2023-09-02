package com.gaffaryucel.artbookhlttestingapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.DataModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import com.gaffaryucel.artbookhlttestingapp.model.SearchModel
import com.gaffaryucel.artbookhlttestingapp.model.SearchResponse
import com.gaffaryucel.artbookhlttestingapp.repo.SearchRepoInterface
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import javax.inject.Inject

@HiltViewModel
class SearchArtViewModel @Inject constructor(
    private val repository : SearchRepoInterface,
    private val repo : WikiArtApiRepoInterface
) : ViewModel() {
    private var mutableResultList = MutableLiveData<List<DataModel>>()
    val resultList : LiveData<List<DataModel>>
        get() = mutableResultList

    private var myResults = ArrayList<DataModel>()
    private val disposable = CompositeDisposable()

    private val artistList = MutableLiveData<List<Artist>>()
    val artistListLiveData : LiveData<List<Artist>>
        get() = artistList

    var tabTitles = MutableLiveData<ArrayList<Int>>()
    var  artGenres = MutableLiveData<ArrayList<SearchModel>>()
    var chronologylist = MutableLiveData<ArrayList<SearchModel>>()

    private var artList =  MutableLiveData<List<HomePageModel>>()
    val artListLiveData : LiveData<List<HomePageModel>>
        get() = artList

    var newsList = MutableLiveData<ArrayList<SearchModel>>()

    init {
        createArtistList()
        createArstList()
        createnewsList()
        createArtList()
        createchronologylist()
        createTabTitles()
    }

    @SuppressLint("CheckResult")
    fun wikiClient(searchText : String) = viewModelScope.launch {
        myResults = ArrayList()
        val call: Single<SearchResponse> = repository.searchArticles(searchText)
        disposable.add(call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val searchResults = response.query.search
                for (result in searchResults) {
                    getArticleDetail(result.pageid, result.title, searchResults.size)
                }
            }, { error ->
                println("İstek başarısız: ${error.localizedMessage}")
            })
        )
    }
    @SuppressLint("CheckResult")
    private fun getArticleDetail(pageId: Int, title: String, listSize : Int) = viewModelScope.launch{
        disposable.add(repository.getPageContent(pageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val pageContent = response.query.pages.values.firstOrNull()?.extract
                if (pageContent != null) {
                    val turkishText = String(pageContent.toByteArray(), Charset.forName("UTF-8"))
                    val data = DataModel(title,turkishText)
                    myResults.add(data)
                    if (myResults.size == listSize){
                        mutableResultList.value = myResults
                    }
                } else {
                    println("Sayfa içeriği bulunamadı.")
                }
            }, { error ->
                println("İstek başarısız: ${error.localizedMessage}")
            })
        )
    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
    private fun createArtistList() = viewModelScope.launch{

        val single = repo.getPopularArtists()

        disposable.add(single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                artistList.postValue(response)
            }, { error ->

            })
        )
    }
    private fun createArstList(){
        artGenres.value = arrayListOf<SearchModel>(
            SearchModel("Painting", R.drawable.painting),
            SearchModel("Sculpture", R.drawable.sculpture),
            SearchModel("Photography", R.drawable.photography),
            SearchModel("Drawing", R.drawable.drawing),
            SearchModel("Installation Art", R.drawable.installation),
            SearchModel("Video Art", R.drawable.videoart),
        )

    }
    private fun createchronologylist(){
        chronologylist.value = arrayListOf<SearchModel>(
            SearchModel("Paleolithic Period", R.drawable.plp),
            SearchModel("Ancient Egypt Period", R.drawable.aep),
            SearchModel("Ancient Greece Period", R.drawable.agp),
            SearchModel("Renaissance Period", R.drawable.renaissance),
            SearchModel("Baroque Period", R.drawable.agp),
            SearchModel("Rococo Period", R.drawable.aep),
            SearchModel("Romanticism Period", R.drawable.renaissance),
            SearchModel("Impressionism Period", R.drawable.agp),
            SearchModel("Surrealism Period", R.drawable.aep),
            SearchModel("Cubism Period", R.drawable.renaissance),
            SearchModel("Pop Art Period", R.drawable.aep),
            SearchModel("Minimalism Period", R.drawable.plp),
            SearchModel("Postmodernism Period", R.drawable.renaissance),
            SearchModel("Paleolithic Period", R.drawable.plp),
            SearchModel("Ancient Egypt Period", R.drawable.aep),
            SearchModel("Ancient Greece Period", R.drawable.agp),
            SearchModel("Renaissance Period", R.drawable.renaissance),
            SearchModel("Baroque Period", R.drawable.agp),
            SearchModel("Rococo Period", R.drawable.aep),
            SearchModel("Romanticism Period", R.drawable.renaissance),
            SearchModel("Impressionism Period", R.drawable.agp),
            SearchModel("Surrealism Period", R.drawable.aep),
            SearchModel("Cubism Period", R.drawable.renaissance),
            SearchModel("Pop Art Period", R.drawable.aep),
            SearchModel("Minimalism Period", R.drawable.plp),
            SearchModel("Postmodernism Period", R.drawable.renaissance)
        )
    }
    private fun createArtList() = viewModelScope.launch{

        val single = repo.getMostViewedArts()

        disposable.add(single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                artList.value = response
            }, { error ->

            })
        )
    }
    private fun createnewsList(){
        newsList.value = arrayListOf<SearchModel>(
            SearchModel("Painting", R.drawable.painting),
            SearchModel("Sculpture", R.drawable.sculpture),
            SearchModel("Photography", R.drawable.photography),
            SearchModel("Drawing", R.drawable.drawing),
            SearchModel("Installation Art", R.drawable.installation),
            SearchModel("Video Art", R.drawable.videoart),
        )
    }
    private fun createTabTitles(){
        tabTitles.value = arrayListOf(
            R.drawable.painter, R.drawable.art, R.drawable.kronoloji, R.drawable.book,
            R.drawable.news
        )
    }

}