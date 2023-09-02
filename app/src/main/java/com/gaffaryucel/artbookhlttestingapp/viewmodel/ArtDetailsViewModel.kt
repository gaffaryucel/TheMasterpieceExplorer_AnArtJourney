package com.gaffaryucel.artbookhlttestingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    private val repo : ArtRepoInterface
) : ViewModel() {

    private var insertArtMsg = MutableLiveData<Resource<ArtModel>>()
    val message: LiveData<Resource<ArtModel>>
        get() = insertArtMsg

    private val selectedImage = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = selectedImage

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    suspend fun updateArt(art: ArtModel) = viewModelScope.launch{
        repo.updateArt(art)
    }

    fun makeArt(name: String, artist: String, year: String) {
        if (name.isEmpty() || artist.isEmpty() || year.isEmpty()) {
            insertArtMsg.value = Resource.error("Please enter every info", null)
            return
        }
        val yearInt = try {
            year.toInt()
            val art = ArtModel(name, artist, year, selectedImage.value ?: "www.google.com", 0)
            insertArtMsg.value = Resource.success(art)
            setSelectedImage("")
        } catch (e: Exception) {
            insertArtMsg.value = Resource.error("year must be Integer ", null)
        }
    }

    fun insertArt(art: ArtModel)= viewModelScope.launch {
        repo.insertArt(art)
    }

    fun deleteArt(artModel: ArtModel) = viewModelScope.launch {
        repo.deleteArt(artModel)
    }
}