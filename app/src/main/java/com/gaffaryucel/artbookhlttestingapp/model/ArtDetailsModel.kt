package com.gaffaryucel.artbookhlttestingapp.model


import com.google.gson.annotations.SerializedName

data class ArtDetailsModel(
    @SerializedName("artistContentId")
    val artistContentId: Int,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artistUrl")
    val artistUrl: String,
    @SerializedName("auction")
    val auction: Any,
    @SerializedName("completitionYear")
    val completitionYear: Int,
    @SerializedName("contentId")
    val contentId: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("diameter")
    val diameter: Any,
    @SerializedName("dictionaries")
    val dictionaries: List<Int>,
    @SerializedName("galleryName")
    val galleryName: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastPrice")
    val lastPrice: Any,
    @SerializedName("location")
    val location: Any,
    @SerializedName("material")
    val material: Any,
    @SerializedName("period")
    val period: Any,
    @SerializedName("serie")
    val serie: Any,
    @SerializedName("sizeX")
    val sizeX: Double,
    @SerializedName("sizeY")
    val sizeY: Double,
    @SerializedName("style")
    val style: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("technique")
    val technique: Any,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("yearAsString")
    val yearAsString: String,
    @SerializedName("yearOfTrade")
    val yearOfTrade: Any
)