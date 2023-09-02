package com.gaffaryucel.artbookhlttestingapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.Hit
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.util.Resource

class FakeArtRepo : ArtRepoInterface {
    private val arts  = mutableListOf<ArtModel>()
    private val artListLiveData = MutableLiveData<List<ArtModel>>(arts)
    override suspend fun searchImage(image: String): Resource<PixabeyModel> {
        return Resource.success(PixabeyModel(arrayListOf(),0,0))
    }

    override fun observeArts(): LiveData<List<ArtModel>> {
        // Return a mocked list of arts.
        return artListLiveData
    }

    override suspend fun updateArt(art: ArtModel) {

    }

    override suspend fun insertArt(art: ArtModel) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(artModel: ArtModel) {
        arts.remove(artModel)
        refreshData()
    }
    private fun refreshData(){
        artListLiveData.postValue(arts)
    }
}