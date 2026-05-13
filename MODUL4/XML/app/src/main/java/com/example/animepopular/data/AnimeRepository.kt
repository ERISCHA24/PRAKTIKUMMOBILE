package com.example.animepopular.data

import com.example.animepopular.model.AnimeItem

object AnimeRepository {

    fun getAnimeList(): List<AnimeItem> = listOf(
        AnimeItem(
            id = 1,
            titleEn = "Attack on Titan",
            titleId = "Serangan Titan",
            year = 2013,
            genreEn = "Action / Dark Fantasy",
            genreId = "Aksi / Fantasi Gelap",
            plotEn = "In a world where humanity lives within enormous walled cities to protect themselves from Titans, gigantic humanoid creatures, young Eren Yeager vows to exterminate all Titans after his mother is consumed by one.",
            plotId = "Di dunia di mana umat manusia hidup dalam kota berdinding raksasa untuk melindungi diri dari Titan, makhluk humanoid raksasa, Eren Yeager yang muda bersumpah untuk memusnahkan semua Titan setelah ibunya dimakan oleh salah satunya.",
            studioEn = "Wit Studio / MAPPA",
            studioId = "Wit Studio / MAPPA",
            episodes = "87 Episodes",
            rating = "★ 9.0 / 10",
            imageUrl = "https://cdn.myanimelist.net/images/anime/10/47347.jpg",
            imdbUrl = "https://www.imdb.com/title/tt2560140/",

        ),
        AnimeItem(
            id = 2,
            titleEn = "Hunter x Hunter",
            titleId = "Hunter x Hunter",
            year = 2011,
            genreEn = "Adventure / Fantasy",
            genreId = "Petualangan / Fantasi",
            plotEn = "Gon Freecss aspires to become a Hunter, a licensed professional who specializes in tracking down fantastical treasures, rare animals, or even other people, to find his missing father who was also a legendary Hunter.",
            plotId = "Gon Freecss bercita-cita menjadi Hunter, seorang profesional berlisensi yang mengkhususkan diri dalam melacak harta ajaib, hewan langka, bahkan orang lain, untuk menemukan ayahnya yang hilang yang juga seorang Hunter legendaris.",
            studioEn = "Madhouse",
            studioId = "Madhouse",
            episodes = "148 Episodes",
            rating = "★ 9.0 / 10",
            imageUrl = "https://cdn.myanimelist.net/images/anime/11/33657.jpg",
            imdbUrl = "https://www.imdb.com/title/tt2098220/"
        ),
        AnimeItem(
            id = 3,
            titleEn = "Gurren Lagann",
            titleId = "Gurren Lagann",
            year = 2007,
            genreEn = "Mecha / Action",
            genreId = "Mecha / Aksi",
            plotEn = "Simon and Kamina were born and raised in a deep underground village. One day they find a small mecha which helps them break through the surface and discover the vast world above, where they must fight against the Spiral King.",
            plotId = "Simon dan Kamina lahir dan dibesarkan di desa bawah tanah. Suatu hari mereka menemukan mecha kecil yang membantu mereka menembus permukaan dan menemukan dunia luas di atas, di mana mereka harus melawan Raja Spiral.",
            studioEn = "Gainax",
            studioId = "Gainax",
            episodes = "27 Episodes",
            rating = "★ 8.7 / 10",
            imageUrl = "https://myanimelist.net/images/anime/4/5123l.jpg",
            imdbUrl = "https://www.imdb.com/title/tt0948103/"
        ),
        AnimeItem(
            id = 4,
            titleEn = "Code Geass",
            titleId = "Code Geass",
            year = 2008,
            genreEn = "Mecha / Drama",
            genreId = "Mecha / Drama",
            plotEn = "A disgraced prince uses a power called Geass to lead a rebellion against the Holy Britannian Empire that has conquered Japan, while hiding his identity as the masked revolutionary Zero.",
            plotId = "Seorang pangeran yang dipermalukan menggunakan kekuatan bernama Geass untuk memimpin pemberontakan melawan Kekaisaran Britannia Suci yang telah menaklukkan Jepang, sambil menyembunyikan identitasnya sebagai revolusioner bertopeng Zero.",
            studioEn = "Sunrise",
            studioId = "Sunrise",
            episodes = "50 Episodes",
            rating = "★ 8.7 / 10",
            imageUrl = "https://myanimelist.net/images/anime/1032/135088l.jpg",
            imdbUrl = "https://www.imdb.com/title/tt0994314/"
        ),
        AnimeItem(
            id = 5,
            titleEn = "Demon Slayer",
            titleId = "Pembasmi Iblis",
            year = 2019,
            genreEn = "Action / Supernatural",
            genreId = "Aksi / Supranatural",
            plotEn = "Tanjiro Kamado becomes a Demon Slayer of the Demon Slayer Corps after his family was slaughtered and his younger sister Nezuko turned into a demon, to avenge their family and cure his sister.",
            plotId = "Tanjiro Kamado menjadi Pembasmi Iblis setelah keluarganya dibantai dan adiknya Nezuko berubah menjadi iblis, untuk membalas dendam keluarga dan menyembuhkan adiknya.",
            studioEn = "ufotable",
            studioId = "ufotable",
            episodes = "44 Episodes",
            rating = "★ 8.7 / 10",
            imageUrl = "https://myanimelist.net/images/anime/1286/99889l.jpg",
            imdbUrl = "https://www.imdb.com/title/tt9335498/"
        ),
        AnimeItem(
            id = 6,
            titleEn = "Trigun",
            titleId = "Trigun",
            year = 1998,
            genreEn = "Sci-Fi / Western",
            genreId = "Fiksi Ilmiah / Barat",
            plotEn = "Vash the Stampede is a legendary gunman with a bounty of 60 billion double dollars on his head. He roams the desert planet Gunsmoke, followed by two insurance agents, though he is actually a pacifist.",
            plotId = "Vash the Stampede adalah penembak legendaris dengan hadiah 60 miliar double dollar di kepalanya. Ia berkeliaran di planet gurun Gunsmoke, diikuti dua agen asuransi, meskipun sebenarnya ia seorang pasifis.",
            studioEn = "Madhouse",
            studioId = "Madhouse",
            episodes = "26 Episodes",
            rating = "★ 8.2 / 10",
            imageUrl = "https://myanimelist.net/images/anime/7/20310l.jpg",
            imdbUrl = "https://www.imdb.com/title/tt0251439/"
        ),
        AnimeItem(
            id = 7,
            titleEn = "Spy x Family",
            titleId = "Fbi x Keluarga",
            year = 2022,
            genreEn = "Comedy / Action",
            genreId = "Komedi / Aksi",
            plotEn = "A spy on a secret mission must build a fake family to complete it. He recruits a telepathic girl as his daughter and an assassin as his wife, neither knowing each other's true identities.",
            plotId = "Seorang mata-mata dalam misi rahasia harus membangun keluarga palsu untuk menyelesaikannya. Ia merekrut gadis telepati sebagai putrinya dan seorang pembunuh bayaran sebagai istrinya, tanpa mengetahui identitas asli satu sama lain.",
            studioEn = "Wit Studio / CloverWorks",
            studioId = "Wit Studio / CloverWorks",
            episodes = "37 Episodes",
            rating = "★ 8.5 / 10",
            imageUrl = "https://myanimelist.net/images/anime/1111/121262l.jpg",
            imdbUrl = "https://www.imdb.com/title/tt13706018/"
        )
    )
}