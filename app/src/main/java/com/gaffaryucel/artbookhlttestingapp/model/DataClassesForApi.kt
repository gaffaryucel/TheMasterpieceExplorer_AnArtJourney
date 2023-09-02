package com.gaffaryucel.artbookhlttestingapp.model

data class SearchResponse(
    val query: QueryResponse
)

data class QueryResponse(
    val search: List<SearchResult>
)

data class SearchResult(
    val title: String,
    val pageid: Int
)

data class PageResponse(
    val query: PageQueryResponse
)

data class PageQueryResponse(
    val pages: Map<String, PageResult>
)

data class PageResult(
    val extract: String
)