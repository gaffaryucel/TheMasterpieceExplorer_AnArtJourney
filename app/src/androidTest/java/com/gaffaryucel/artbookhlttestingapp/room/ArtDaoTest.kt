package com.gaffaryucel.artbookhlttestingapp.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.gaffaryucel.artbookhlttestingapp.getOrAwaitValueTest
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ArtDaoTest {


    private lateinit var dao : ArtDao
    private lateinit var db : ArtDatabase

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ArtDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.artDao()
    }

    @After
    fun teardown(){
        db.close()
    }

    @Test
    fun insertArtTesting() {
        GlobalScope.launch(Dispatchers.Main) {
            val exampleArt = ArtModel("asd","asd","aqsd","asd",1)
            dao.insertArt(exampleArt)
            val artrlist = dao.observeArts().getOrAwaitValueTest()
            assertThat(artrlist).contains(exampleArt)
        }
    }
}