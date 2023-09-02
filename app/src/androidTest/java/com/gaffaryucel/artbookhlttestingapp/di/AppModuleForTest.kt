package com.gaffaryucel.artbookhlttestingapp.di

import android.content.Context
import androidx.room.Room
import com.gaffaryucel.artbookhlttestingapp.room.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModuleForTest {

    @Provides
    @Named("testDataBase")
    fun injectInMemoryRoom(@ApplicationContext context : Context) = Room.inMemoryDatabaseBuilder(
        context,ArtDatabase::class.java
    ).allowMainThreadQueries().build()

}