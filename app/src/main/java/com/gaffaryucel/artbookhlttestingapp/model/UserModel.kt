package com.gaffaryucel.artbookhlttestingapp.model

import com.google.gson.annotations.SerializedName

class UserModel{
    @SerializedName("name")
    var name : String? = null
    @SerializedName("email")
    var email : String? = null
    @SerializedName("profilePhoto")
    var profilePhoto : String? = null

    constructor()

    constructor(
        name : String,
        email : String,
        profilePhoto : String,
    ){
        this.name = name
        this.email = email
        this.profilePhoto = profilePhoto
    }
}
