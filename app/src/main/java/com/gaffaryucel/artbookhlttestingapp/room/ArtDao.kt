package com.gaffaryucel.artbookhlttestingapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel

@Dao
interface ArtDao {
    @Query("SELECT * FROM Arts")
    fun observeArts(): LiveData<List<ArtModel>>

    @Update
    suspend fun updateArt(art: ArtModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art : ArtModel)

    @Delete
    suspend fun deleteArt(artModel: ArtModel)
}