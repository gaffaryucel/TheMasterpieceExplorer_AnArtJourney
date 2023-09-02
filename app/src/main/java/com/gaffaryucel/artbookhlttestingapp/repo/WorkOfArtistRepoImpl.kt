package com.gaffaryucel.artbookhlttestingapp.repo

import com.gaffaryucel.artbookhlttestingapp.api.WorkOfArtistService
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.ArtDetailsModel
import io.reactivex.Single
import javax.inject.Inject

class WorkOfArtistRepoImpl @Inject constructor(
    private val workOfArtistService: WorkOfArtistService
) : WorkOfArtistRepoInterface {
    override suspend fun getPaintingsByArtist(artistUrl: String): Single<List<Art>> {
        return workOfArtistService.getPaintingsByArtist(artistUrl)
    }

    override suspend fun getArtDetails(contentid: String): Single<ArtDetailsModel> {
        return workOfArtistService.getArtDetails(contentid)
    }
}