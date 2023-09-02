package com.gaffaryucel.artbookhlttestingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoInterface
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchImageViewModel @Inject constructor(
    private val repo : ArtRepoInterface
): ViewModel() {
    private val images = MutableLiveData<Resource<PixabeyModel>>()
    val imageList : LiveData<Resource<PixabeyModel>>
        get() = images

    fun searchImage(searchString : String){
        if (searchString.isNotEmpty()){
            return
        }else{
            images.value = Resource.loading(null)
            viewModelScope.launch {
                val response = repo.searchImage(searchString)
                images.value = response
            }
        }
    }
}