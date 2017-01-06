package com.wykorijnsburger.movietimes.backend.movie.tmdb


data class TMDBSearchResult(
        val page: Int,
        val results: List<TMDBMovie>
)