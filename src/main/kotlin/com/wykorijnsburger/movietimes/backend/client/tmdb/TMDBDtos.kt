package com.wykorijnsburger.movietimes.backend.client.tmdb


data class TMDBDetailsResult(val id: String,
                             val imdb_id: String,
                             val runtime: String,
                             val videos: TMDBVideosResult = TMDBVideosResult())

data class TMDBVideosResult(val results: List<TMDBVideoResult> = emptyList())

data class TMDBVideoResult(val id: String,
                           val type: String,
                           val site: String,
                           val key: String)
