package com.wykorijnsburger.cineville.backend.movie.tmdb

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TMDBService {
    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("api_key") apiKey: String): Observable<TMDBSearchResult>
}