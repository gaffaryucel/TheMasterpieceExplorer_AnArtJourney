package com.gaffaryucel.artbookhlttestingapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel

@Database(entities = [ArtModel::class], version = 2)
abstract class ArtDatabase  : RoomDatabase(){
    abstract fun artDao(): ArtDao
}