package com.gaffaryucel.artbookhlttestingapp.repo

import com.gaffaryucel.artbookhlttestingapp.model.PageResponse
import com.gaffaryucel.artbookhlttestingapp.model.SearchResponse
import io.reactivex.Single

interface SearchRepoInterface {
    suspend fun searchArticles(searchText: String): Single<SearchResponse>
    suspend fun getPageContent(pageId: Int): Single<PageResponse>
}