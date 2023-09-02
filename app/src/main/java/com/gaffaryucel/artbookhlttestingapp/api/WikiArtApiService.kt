package com.gaffaryucel.artbookhlttestingapp.api

import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.ArtDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.ArtistDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import com.gaffaryucel.artbookhlttestingapp.viewmodel.ArtistDetailsViewModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WikiArtApiService {
    @GET("/en/app/api/popularartists?json=1")
    fun getPopularArtists(): Single<List<Artist>>

    @GET("/en/App/Painting/MostViewedPaintings?randomSeed=123&json=2&inPublicDomain=false")
    fun getMostViewedArts() : Single<List<HomePageModel>>

    @GET("/en/{name}")
    fun getArtistDetails(@Path("name") name : String,@Query("json") json: Int = 2): Single<ArtistDetailsModel>

   //https://www.wikiart.org/en/App/Painting/ImageJson/212081
}
/*
    @GET("/en/search/Leonardo%20Da%20Vinci/1?json=2&PageSize=20")
    fun searchInWikiArtApi(): Single<List<Artist>>
 */