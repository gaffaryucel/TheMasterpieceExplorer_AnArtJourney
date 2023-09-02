package com.gaffaryucel.artbookhlttestingapp.model

data class HomePageModel (
    val title: String,
    val contentId: Int,
    val artistContentId: Int,
    val artistName: String,
    val completitionYear: Int,
    val yearAsString: String,
    val width: Int,
    val image: String,
    val height: Int
)