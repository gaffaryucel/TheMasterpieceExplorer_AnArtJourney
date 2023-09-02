package com.gaffaryucel.artbookhlttestingapp.api

import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.ArtDetailsModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkOfArtistService {
    @GET("/en/App/Painting/PaintingsByArtist")
    fun getPaintingsByArtist(@Query("artistUrl") artistUrl: String, @Query("json") json: Int = 2): Single<List<Art>>

    @GET("en/App/Painting/ImageJson/{contentid}")
    fun getArtDetails(@Path("contentid") contentid : String): Single<ArtDetailsModel>

}