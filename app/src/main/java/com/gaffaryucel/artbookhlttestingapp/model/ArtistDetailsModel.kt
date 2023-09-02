package com.gaffaryucel.artbookhlttestingapp.model

data class ArtistDetailsModel(
    val relatedArtistsIds: List<Int>, // İlgili sanatçılara ait ID'lerin listesi
    val OriginalArtistName: String, // Orijinal sanatçının tam adı
    val gender: String, // Sanatçının cinsiyeti
    val story: String, // Sanatçının hikayesi için URL
    val biography: String, // Sanatçının biyografisi
    val activeYearsCompletion: Int, // Aktif yılların tamamlanma yılı
    val activeYearsStart: Int, // Aktif yılların başlama yılı
    val series: String, // Sanatçının seri adı
    val themes: String, // Sanatçının temaları
    val periodsOfWork: String, // Sanatçının çalışma dönemleri
    val contentId: Int, // Sanatçı içerik ID'si
    val artistName: String, // Sanatçının adı
    val url: String, // Sanatçıyla ilgili URL
    val lastNameFirst: String, // Sanatçının soyadı ile adı arasında ters çevrilmiş tam adı
    val birthDay: String, // Sanatçının doğum günü için tarih
    val deathDay: String, // Sanatçının ölüm günü için tarih
    val birthDayAsString: String, // Sanatçının doğum günü (String olarak)
    val deathDayAsString: String, // Sanatçının ölüm günü (String olarak)
    val image: String, // Sanatçının resmi için URL
    val wikipediaUrl: String, // Sanatçı hakkında Wikipedia URL'si
    val dictonaries: List<Int> // Sözlük ID'lerinin listesi
)

