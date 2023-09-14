package com.gaffaryucel.artbookhlttestingapp.adapter

import android.view.View

interface UrlListener {
    fun getUrl(url : String)
}
interface IdListener {
    fun getId(id : String,view : View)
}
interface IdListenerFr {
    fun getId(id : String)
}

