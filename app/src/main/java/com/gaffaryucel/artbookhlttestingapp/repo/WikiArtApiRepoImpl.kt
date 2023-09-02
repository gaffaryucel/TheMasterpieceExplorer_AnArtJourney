package com.gaffaryucel.artbookhlttestingapp.repo

import com.gaffaryucel.artbookhlttestingapp.api.WikiArtApiService
import com.gaffaryucel.artbookhlttestingapp.model.ArtDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.ArtistDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import io.reactivex.Single
import javax.inject.Inject

class WikiArtApiRepoImpl @Inject constructor(
    private val wikiartApi: WikiArtApiService
) : WikiArtApiRepoInterface {
    override suspend fun getPopularArtists(): Single<List<Artist>> {
        return wikiartApi.getPopularArtists()
    }

    override suspend fun getMostViewedArts(): Single<List<HomePageModel>> {
        return wikiartApi.getMostViewedArts()
    }

    override suspend fun getArtistDetails(artist : String): Single<ArtistDetailsModel> {
        return wikiartApi.getArtistDetails(artist)
    }

}