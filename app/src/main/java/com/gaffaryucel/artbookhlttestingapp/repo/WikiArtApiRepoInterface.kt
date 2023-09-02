package com.gaffaryucel.artbookhlttestingapp.repo

import com.gaffaryucel.artbookhlttestingapp.model.ArtDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.ArtistDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import io.reactivex.Single

interface WikiArtApiRepoInterface {
    suspend fun getPopularArtists(): Single<List<Artist>>
    suspend fun getMostViewedArts() : Single<List<HomePageModel>>
    suspend fun getArtistDetails(artist : String) : Single<ArtistDetailsModel>

}