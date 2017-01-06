package com.wykorijnsburger.movietimes.backend.client.tmdb

import reactor.core.publisher.Flux
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("api_key") apiKey: String): Flux<TMDBSearchResult>
}