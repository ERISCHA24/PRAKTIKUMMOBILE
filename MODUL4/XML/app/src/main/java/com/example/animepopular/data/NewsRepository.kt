package com.example.animepopular.data
import com.example.animepopular.model.NewsItem

object NewsRepository {
    fun getNewsList(): List<NewsItem> = listOf(
        NewsItem(
            NewsEn = "Attack on Titan Lunching a NEW SEASON",
            NewsIn = "Season Baru Attack on Titan",
            ContentEn = "Season final Attack of Titan got confirmation from MAPPA in this autumn.",
            ContentIn = "Konfirmasi season final akan tayang musim gugur ini.",
            UpdateEn = "2 hour ago",
            UpdateIn = "2 jam lalu",
        ),
        NewsItem(
            NewsEn = "Demon Slayer The Movie coming soon",
            NewsIn = "Film Demon Slayer Diumumkan",
            ContentEn = "ufutable giving the confirmation about a new movie demon slayer next year.",
            ContentIn = "ufotable mengumumkan film layar lebar baru yang akan tayang tahun depan.",
            UpdateEn = "5 hour ago",
            UpdateIn = "5 jam lalu",
        ),
        NewsItem(
            NewsEn = "Spy x Family Season 3",
            NewsIn = "Spy x Family Season 3 akan tayang",
            ContentEn = "Spy x Family will airing in november 2027.",
            ContentIn = "Wit Studio mengonfirmasi produksi season 3 sudah dimulai.",
            UpdateEn = "1 day ago",
            UpdateIn = "1 hari lalu",
        ),
        NewsItem(
            NewsEn = "Anime Awards 2024",
            NewsIn = "Ajang Penghargaan Anime Tahun 2024",
            ContentEn = "Attack of Titan for 3 year in a row winning Anime of the Year Awards.",
            ContentIn = "Attack on Titan memenangkan Anime of the Year untuk ketiga kalinya.",
            UpdateEn = "2 day ago",
            UpdateIn = "2 hari lalu",
        ),
        NewsItem(
            NewsEn = "Hunter x Hunter launcing a new Game",
            NewsIn = "Hunter x Hunter Game Baru",
            ContentEn = "Hunter x Hunter Announcment a new RPG Game for Console.",
            ContentIn = "Game RPG berbasis Hunter x Hunter diumumkan untuk konsol.",
            UpdateEn = "3 day ago",
            UpdateIn = "3 hari lalu",
        ),
    )
}
