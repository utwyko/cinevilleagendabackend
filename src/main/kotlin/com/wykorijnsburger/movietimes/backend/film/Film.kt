package com.wykorijnsburger.movietimes.backend.film


data class Film(val title: String,
                val posterUrl: String? = null,
                val stillUrl: String? = null,
                val trailerUrl: String? = null,
                val directors: List<String> = emptyList(),
                val cast: List<String> = emptyList(),
                val language: String? = null,
                val oneLiner: String? = null,
                val year: String? = null,
                val cinevilleId: String? = null,
                val teaser: String? = null
                ) {
    fun isEmpty(): Boolean {
        return this == emptyFilm()
    }
}

fun emptyFilm(): Film {
    return Film(title = "", posterUrl = "")
}