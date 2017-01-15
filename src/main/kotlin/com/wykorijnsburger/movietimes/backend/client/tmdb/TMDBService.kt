package com.wykorijnsburger.movietimes.backend.client.tmdb

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("api_key") apiKey: String): Mono<TMDBSearchResult>

    @GET("movie/{movie_id}/videos")
    fun getVideos(@Path("movie_id") movieId: String, @Query("api_key") apiKey: String): Mono<TMDBVideoResult>
}

data class TMDBVideoResults(val id: String,
                            val results: List<TMDBVideoResult>)

data class TMDBVideoResult(val id: String,
                           val type: String,
                           val site: String)
