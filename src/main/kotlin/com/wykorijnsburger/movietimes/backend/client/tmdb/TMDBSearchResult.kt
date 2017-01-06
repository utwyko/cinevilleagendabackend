package com.wykorijnsburger.movietimes.backend.client.tmdb


data class TMDBSearchResult(
        val page: Int,
        val results: List<TMDBMovie>
)