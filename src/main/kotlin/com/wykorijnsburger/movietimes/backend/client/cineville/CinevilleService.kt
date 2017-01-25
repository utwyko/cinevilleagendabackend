package com.wykorijnsburger.movietimes.backend.client.cineville

import reactor.core.publisher.Mono
import retrofit2.http.GET
import retrofit2.http.Query


interface CinevilleService {
    @GET("shows.json")
    fun getShowtimes(@Query("query") query: String, @Query("rows") limit: Int = 10): Mono<List<CinevilleShowtime>>

    @GET("films.json")
    fun getFilms(@Query("query") query: String?, @Query("rows") limit: Int?): Mono<List<CinevilleFilm>>
}

data class CinevilleFilm(val id: String,
                         val year: String?,
                         val title: String,
                         val type: String,
                         val changed: String,
                         val created: String,
                         val path: String,
                         val oneliner: String,
                         val still: String? = null,
                         val poster: String,
                         val keywords: List<String>,
                         val directors: List<String>,
                         val countries: List<String>,
                         val cast: List<String>,
                         val language: String,
                         val teaser: String? = null) {
    fun isEmpty(): Boolean {
        return this == emptyFilm()
    }
}

fun emptyFilm(): CinevilleFilm {
    return CinevilleFilm("", "", "", "", "", "", "", "", "", "", emptyList(), emptyList(), emptyList(), emptyList(), "")
}

