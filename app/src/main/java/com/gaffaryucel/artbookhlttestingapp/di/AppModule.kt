package com.gaffaryucel.artbookhlttestingapp.di

import android.content.Context
import androidx.room.Room
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.api.ApiInterface
import com.gaffaryucel.artbookhlttestingapp.api.WikiArtApiService
import com.gaffaryucel.artbookhlttestingapp.api.WikipediaService
import com.gaffaryucel.artbookhlttestingapp.api.WorkOfArtistService
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoImpl
import com.gaffaryucel.artbookhlttestingapp.repo.ArtRepoInterface
import com.gaffaryucel.artbookhlttestingapp.repo.SearchRepoImpl
import com.gaffaryucel.artbookhlttestingapp.repo.SearchRepoInterface
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoImpl
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoInterface
import com.gaffaryucel.artbookhlttestingapp.repo.WorkOfArtistRepoImpl
import com.gaffaryucel.artbookhlttestingapp.repo.WorkOfArtistRepoInterface
import com.gaffaryucel.artbookhlttestingapp.room.ArtDao
import com.gaffaryucel.artbookhlttestingapp.room.ArtDatabase
import com.gaffaryucel.artbookhlttestingapp.util.Util.BASE_URL
import com.gaffaryucel.artbookhlttestingapp.util.Util.WIKI_ART_URL
import com.gaffaryucel.artbookhlttestingapp.util.Util.WIKI_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
    @Provides
    @Singleton
    fun provideVikiApi(): WikipediaService {
        val gson: Gson = GsonBuilder().create()
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(WIKI_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(WikipediaService::class.java)
    }
    @Provides
    @Singleton
    fun provideWikiArtApiService() : WikiArtApiService{
        val gson: Gson = GsonBuilder().create()
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(WIKI_ART_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(WikiArtApiService::class.java)

    }

    @Provides
    @Singleton
    fun provideWorkOfArtist() : WorkOfArtistService {
        val gson: Gson = GsonBuilder().create()
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(WIKI_ART_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(WorkOfArtistService::class.java)
    }

    @Provides
    @Singleton
    fun provideWorkOfArtistRepo(workOfArtistService: WorkOfArtistService): WorkOfArtistRepoInterface =
        WorkOfArtistRepoImpl(workOfArtistService)

    @Provides
    @Singleton
    fun provideWikiArtApiRepo(wikiArtApiService: WikiArtApiService) : WikiArtApiRepoInterface =
        WikiArtApiRepoImpl(wikiArtApiService)

    @Provides
    @Singleton
    fun provideSearchRepo(api : WikipediaService): SearchRepoInterface {
        return SearchRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ArtDatabase::class.java,
            "Database_Version"
        ).build()

    @Provides
    @Singleton
    fun provideDao(database: ArtDatabase) : ArtDao {
        return database.artDao()
    }


    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context) : RequestManager  {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        return Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.image)
                    .placeholder(circularProgressDrawable)
            )
    }
    @Provides
    @Singleton
    fun provideRepo(api: ApiInterface,dao : ArtDao): ArtRepoInterface {
        return ArtRepoImpl(api,dao)
    }
}