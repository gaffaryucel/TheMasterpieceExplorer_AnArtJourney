package com.gaffaryucel.artbookhlttestingapp.repo

import androidx.lifecycle.LiveData
import com.gaffaryucel.artbookhlttestingapp.api.ApiInterface
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.room.ArtDao
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import javax.inject.Inject

class ArtRepoImpl @Inject constructor(
    private val retrofit: ApiInterface,
    private val dao: ArtDao
) : ArtRepoInterface {
    override suspend fun searchImage(image: String): Resource<PixabeyModel> {
        return try {
            val response = retrofit.imageSearch(image)
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error("error", null)
            }
        } catch (e: Exception) {
            Resource.error("no data", null)
        }
    }

    override fun observeArts(): LiveData<List<ArtModel>> {
        return dao.observeArts()
    }

    override suspend fun updateArt(art: ArtModel) {
        dao.updateArt(art)
    }

    override suspend fun insertArt(art: ArtModel) {
        dao.insertArt(art)
    }

    override suspend fun deleteArt(artModel: ArtModel) {
        dao.deleteArt(artModel)
    }
}
