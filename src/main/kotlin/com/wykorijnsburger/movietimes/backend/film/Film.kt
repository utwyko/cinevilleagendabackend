package com.wykorijnsburger.movietimes.backend.film

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleFilm
import com.wykorijnsburger.movietimes.backend.client.tmdb.TMDBDetailsResult
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Film(
        @Id
        val cinevilleId: Int,
        val title: String,
        val posterUrl: String? = null,
        val stillUrl: String? = null,
        val trailerUrl: String? = null,
        @ElementCollection
        val directors: List<String>,
        @ElementCollection
        val cast: List<String>,
        val language: String? = null,
        @Column(columnDefinition="text")
        val oneLiner: String? = null,
        val year: String? = null,
        @Column(columnDefinition="text")
        val teaser: String? = null,
        val runtime: Int? = null
) {
    fun isEmpty(): Boolean {
        return this == emptyFilm()
    }
}

fun emptyFilm(): Film {
    return Film(title = "", posterUrl = "", cinevilleId = 0, directors = emptyList(), cast = emptyList())
}

fun compose(cinevilleFilm: CinevilleFilm, tmdbDetailsResult: TMDBDetailsResult): Film {
    val runtime = if (tmdbDetailsResult.isEmpty()) null else tmdbDetailsResult.runtime

    val trailerUrl = tmdbDetailsResult.videos.results
            .filter { it.site.toLowerCase() == "youtube" }
            .map { "https://www.youtube.com/watch?v=${it.key}" }
            .firstOrNull()

    return Film(title = cinevilleFilm.title,
            language = cinevilleFilm.language,
            posterUrl = cinevilleFilm.poster,
            year = cinevilleFilm.year,
            directors = cinevilleFilm.directors.orEmpty(),
            cast = cinevilleFilm.cast.orEmpty(),
            oneLiner = cinevilleFilm.oneliner,
            cinevilleId = cinevilleFilm.id.toInt(),
            teaser = cinevilleFilm.teaser,
            stillUrl = cinevilleFilm.still,
            runtime =  runtime?.toInt(),
            trailerUrl = trailerUrl)
}