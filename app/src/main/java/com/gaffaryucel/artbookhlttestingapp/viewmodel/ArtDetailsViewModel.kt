package com.gaffaryucel.artbookhlttestingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val repo : ArtRepoInterface
) : ViewModel() {

    val arts = repo.observeArts()

    private var insertArtMsg = MutableLiveData<Resource<ArtModel>>()
    val message: LiveData<Resource<ArtModel>>
        get() = insertArtMsg

    private val selectedImage = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = selectedImage

    private fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun updateArt(name: String, artist: String, year: String,url : String,id : Int) = viewModelScope.launch{
        insertArtMsg.value = Resource.loading( null)
        if (name.isEmpty() && artist.isEmpty() && year.isEmpty()) {
            insertArtMsg.value = Resource.error("Please enter every info", null)
            return@launch
        }
        val art = ArtModel(name, artist, year, url , id)
        updateArt(art)
        delay(500)
        insertArtMsg.value = Resource.success( null)
    }

    private fun updateArt(art : ArtModel) = viewModelScope.launch{
        repo.updateArt(art)
    }


    fun makeArt(name: String, artist: String, year: String,url : String) = viewModelScope.launch{
        insertArtMsg.value = Resource.loading( null)
        if (name.isEmpty() || artist.isEmpty() || year.isEmpty()) {
            insertArtMsg.value = Resource.error("Please enter every info", null)
            return@launch
        }
        val art = ArtModel(name, artist, year, url , null)
        insertArt(art)
        insertArtMsg.value = Resource.success( null)
    }

    private fun insertArt(art: ArtModel)= viewModelScope.launch {
        repo.insertArt(art)
    }

    fun deleteArt(artModel: ArtModel) = viewModelScope.launch {
        repo.deleteArt(artModel)
    }

}