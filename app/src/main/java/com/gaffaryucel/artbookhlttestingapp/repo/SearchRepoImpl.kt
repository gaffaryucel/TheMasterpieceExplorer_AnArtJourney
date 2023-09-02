package com.gaffaryucel.artbookhlttestingapp.repo

import com.gaffaryucel.artbookhlttestingapp.api.ApiInterface
import com.gaffaryucel.artbookhlttestingapp.api.WikipediaService
import com.gaffaryucel.artbookhlttestingapp.model.PageResponse
import com.gaffaryucel.artbookhlttestingapp.model.SearchResponse
import io.reactivex.Single
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val service: WikipediaService,
) : SearchRepoInterface{
    override suspend fun searchArticles(searchText: String): Single<SearchResponse> {
        return service.searchArticles(searchText)
    }

    override suspend fun getPageContent(pageId: Int): Single<PageResponse> {
        return service.getPageContent(pageId)
    }
}