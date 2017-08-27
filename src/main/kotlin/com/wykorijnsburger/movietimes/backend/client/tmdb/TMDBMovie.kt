package com.wykorijnsburger.movietimes.backend.client.tmdb

import com.fasterxml.jackson.annotation.JsonProperty

data class TMDBMovie(
        @JsonProperty("poster_path") val posterPath: String?,
        val id: String,
        @JsonProperty( "original_title") val originalTitle: String?,
        val title: String,
        val overview: String,
        @JsonProperty("release_date") val releaseDate: String?,

        val adult: Boolean,
        @JsonProperty("genre_ids") val genreIds: List<String>,
        @JsonProperty("original_language") val originalLanguage: String,
        val popularity: Double,

        @JsonProperty("backdrop_path") val backdropPath: String?,
        @JsonProperty("video") val hasVideo: Boolean,

        @JsonProperty("vote_average") val voteAverage: Double
)