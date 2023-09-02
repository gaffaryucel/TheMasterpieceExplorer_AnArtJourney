package com.gaffaryucel.artbookhlttestingapp.model

data class Artist(
    val contentId: Long,
    val artistName: String,
    val url: String,
    val lastNameFirst: String,
    val birthDay: String,
    val deathDay: String,
    val birthDayAsString: String,
    val deathDayAsString: String,
    val image: String,
    val wikipediaUrl: String,
    val dictionaries: List<Int>
)