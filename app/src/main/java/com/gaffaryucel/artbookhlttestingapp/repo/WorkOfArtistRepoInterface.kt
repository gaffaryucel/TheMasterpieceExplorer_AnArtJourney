package com.gaffaryucel.artbookhlttestingapp.repo

import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.ArtDetailsModel
import io.reactivex.Single

interface WorkOfArtistRepoInterface {
    suspend fun getPaintingsByArtist(artistUrl: String): Single<List<Art>>
    suspend fun getArtDetails(contentid : String) :Single<ArtDetailsModel>
}