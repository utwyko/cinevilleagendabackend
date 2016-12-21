package com.wykorijnsburger.cineville.backend.movie.tmdb


data class TMDBSearchResult(
        val page: Int,
        val results: List<TMDBMovie>
)