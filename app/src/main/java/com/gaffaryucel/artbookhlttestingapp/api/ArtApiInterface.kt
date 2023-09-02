package com.gaffaryucel.artbookhlttestingapp.api

import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel
import com.gaffaryucel.artbookhlttestingapp.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey : String =API_KEY
    ) : Response<PixabeyModel>
}