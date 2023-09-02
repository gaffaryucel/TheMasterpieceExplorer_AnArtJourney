package com.gaffaryucel.artbookhlttestingapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Arts")
data class ArtModel(
    @ColumnInfo(name = "artName")
    val artName : String,
    @ColumnInfo(name = "artistName")
    val artistName : String,
    @ColumnInfo(name = "date")
    val date : String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl : String,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)

