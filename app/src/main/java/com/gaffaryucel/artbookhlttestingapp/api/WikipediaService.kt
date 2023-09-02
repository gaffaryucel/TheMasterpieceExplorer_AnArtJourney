package com.gaffaryucel.artbookhlttestingapp.api

import com.gaffaryucel.artbookhlttestingapp.model.PageResponse
import com.gaffaryucel.artbookhlttestingapp.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaService {
    @GET("/w/api.php?action=query&format=json&list=search")
    fun searchArticles(
        @Query("srsearch") searchQuery: String
    ): Single<SearchResponse>
    //amedeo-modigliani

    @GET("/w/api.php?action=query&format=json&prop=extracts&exintro=1&explaintext=1")
    fun getPageContent(
        @Query("pageids") pageId: Int
    ): Single<PageResponse>
}