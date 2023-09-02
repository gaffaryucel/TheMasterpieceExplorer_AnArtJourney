package com.gaffaryucel.artbookhlttestingapp.repo

import androidx.lifecycle.LiveData
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.util.Resource

interface ArtRepoInterface {
    suspend fun searchImage(image: String) : Resource<PixabeyModel>
    fun observeArts(): LiveData<List<ArtModel>>
    suspend fun updateArt(art: ArtModel)
    suspend fun insertArt(art: ArtModel)
    suspend fun deleteArt(artModel: ArtModel)
}