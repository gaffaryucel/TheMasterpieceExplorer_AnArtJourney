package com.gaffaryucel.artbookhlttestingapp.model

class FirebaseArtistModel {
    var name : String? = null
    var image : String? =null
    var url : String? = null
    constructor()
    constructor(
        name : String,
        image : String,
        url : String
    ){
        this.name = name
        this.image= image
        this.url = url
    }
}