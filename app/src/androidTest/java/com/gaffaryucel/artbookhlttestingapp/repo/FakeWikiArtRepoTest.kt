package com.gaffaryucel.artbookhlttestingapp.repo

import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.ArtistDetailsModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import io.reactivex.observers.TestObserver
import org.junit.Before

class FakeWikiArtRepoTest :  WikiArtApiRepoInterface {

    override suspend fun getPopularArtists(): Single<List<Artist>> {
        val artists = listOf(
            Artist(contentId = 1, artistName = "Leonardo da Vinci", url = "https://en.wikipedia.org/wiki/Leonardo_da_Vinci", lastNameFirst = "da Vinci, Leonardo",
            birthDay = "1452-04-15",
            deathDay = "1519-05-02",
            birthDayAsString = "15 Nisan 1452",
            deathDayAsString = "2 Mayıs 1519",
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Leonardo_da_Vinci_Self-Portrait_%28School_of_Athens%29.jpg/1200px-Leonardo_da_Vinci_Self-Portrait_%28School_of_Athens%29.jpg",
            wikipediaUrl = "https://en.wikipedia.org/wiki/Leonardo_da_Vinci",
            dictionaries = listOf(1, 2, 3))
        )
        return Single.just(artists)
    }

    override suspend fun getMostViewedArts(): Single<List<HomePageModel>> {
        val arts = listOf(
            HomePageModel(
                title = "Mona Lisa",
                contentId = 1,
                artistContentId = 1,
                artistName = "Leonardo da Vinci",
                completitionYear = 1517,
                yearAsString = "1517",
                width = 77,
                height = 53,
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Mona_Lisa,_Leonardo_da_Vinci,_oil_on_poplar_panel,_c._1503.jpg/1200px-Mona_Lisa,_Leonardo_da_Vinci,_oil_on_poplar_panel,_c._1503.jpg"
            )
        )
        return Single.just(arts)
    }

    override suspend fun getArtistDetails(artist: String): Single<ArtistDetailsModel> {
        val artistDetailsModel = ArtistDetailsModel(
            relatedArtistsIds = listOf(1, 2, 3),
            OriginalArtistName = "Leonardo da Vinci",
            gender = "Erkek",
            story = "Leonardo da Vinci, Rönesans döneminin İtalyan ressam, heykeltıraş, mimar, mühendis, anatomist, jeolog, astronom, botanikçi, yazar ve müzisyeniydi. Genellikle bütün zamanların en büyük sanatçılarından ve vizyonerlerinden biri olarak kabul edilir.",
            biography = "Leonardo da Vinci, 1452'de İtalya'nın Vinci kentinde doğdu. Babası florentin noter Piero da Vinci, annesi ise köylü kadın Caterina di Meo Lippi'ydi. Da Vinci, 1466'da Floransa'daki Andrea del Verrocchio'nun atölyesinde çıraklığa başladı. Verrocchio'nun atölyesinde resim, heykel, mimari ve metal işleme gibi çeşitli sanat dallarında eğitim gördü. 1472'de Floransa Ressamlar Loncası'na kabul edildi.",
            activeYearsCompletion = 1519,
            activeYearsStart = 1452,
            series = "Mona Lisa, Son Akşam Yemeği, Sistine Şapeli Tavanı",
            themes = "İnsan anatomisi, makineler, uçaklar, denizcilik, mimarlık, resim, heykel",
            periodsOfWork = "Rönesans",
            contentId = 1,
            artistName = "Leonardo da Vinci",
            url = "https://en.wikipedia.org/wiki/Leonardo_da_Vinci",
            lastNameFirst = "da Vinci, Leonardo",
            birthDay = "1452-04-15",
            deathDay = "1519-05-02",
            birthDayAsString = "15 Nisan 1452",
            deathDayAsString = "2 Mayıs 1519",
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/Leonardo_da_Vinci_Self-Portrait_%28School_of_Athens%29.jpg/1200px-Leonardo_da_Vinci_Self-Portrait_%28School_of_Athens%29.jpg",
            wikipediaUrl = "https://en.wikipedia.org/wiki/Leonardo_da_Vinci",
            dictonaries = listOf(1, 2, 3)
        )
        return Single.just(artistDetailsModel)
    }
}