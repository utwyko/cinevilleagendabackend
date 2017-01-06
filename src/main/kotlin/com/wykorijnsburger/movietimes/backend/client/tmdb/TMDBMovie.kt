package com.wykorijnsburger.movietimes.backend.client.tmdb

import com.squareup.moshi.Json

data class TMDBMovie(
        @Json(name = "poster_path") val posterPath: String,
        val id: Int,
        @Json(name = "original_title") val originalTitle: String,
        val title: String,
        val overview: String,
        @Json(name = "release_date") val releaseDate: String,

        val adult: Boolean,
        @Json(name = "genre_ids") val genreIds: List<String>,
        @Json(name = "original_language") val originalLanguage: String,
        val popularity: Double,

        @Json(name = "backdrop_path") val backdropPath: String?,
        @Json(name = "video") val hasVideo: Boolean,

        val voteCount: Int,
        @Json(name = "vote_average") val voteAverage: Double
)