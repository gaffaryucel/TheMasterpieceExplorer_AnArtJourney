package com.gaffaryucel.artbookhlttestingapp.model

data class Art(
    val title: String,
    val contentId: Long,
    val artistContentId: Long,
    val artistName: String,
    val completitionYear: Long,
    val yearAsString: String,
    val width: Long,
    val image: String,
    val height: Long
)

