package com.gaffaryucel.artbookhlttestingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchImageViewModel @Inject constructor(
    private val repo : ArtRepoInterface
): ViewModel() {
    private val images = MutableLiveData<Resource<PixabeyModel>>()
    val imageList : LiveData<Resource<PixabeyModel>>
        get() = images

    private var messageMld = MutableLiveData<Resource<ArtModel>>()
    val message : LiveData<Resource<ArtModel>>
        get() = messageMld
    init {
        searchImage("ilk")
    }

    fun searchImage(searchString : String) {
        messageMld.value = Resource.loading(null)
        if (searchString.isEmpty()){
            return
        }else if(searchString.equals("ilk")){
            viewModelScope.launch{
                val response = repo.searchImage(" ")
                images.value = response
                messageMld.value = Resource.success(null)
            }
        }else{
            viewModelScope.launch{
                val response = repo.searchImage(searchString)
                images.value = response
                messageMld.value = Resource.success(null)
            }
        }
    }
}