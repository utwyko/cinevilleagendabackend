package com.wykorijnsburger.movietimes.backend.client.tmdb

import reactor.core.publisher.Mono
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {
    @GET("search/movie")
    fun searchMovies(@Query("query") query: String, @Query("api_key") apiKey: String): Mono<TMDBSearchResult>

    @GET("movie/{movie_id}?append_to_response=videos")
    fun getMovieDetailsWithVideos(@Path("movie_id") movieId: String, @Query("api_key") apiKey: String): Mono<TMDBDetailsResult>
}

data class TMDBDetailsResult(val id: String,
                             val imdb_id: String,
                             val runtime: String,
                             val videos: TMDBVideosResult = TMDBVideosResult())

data class TMDBVideosResult(val results: List<TMDBVideoResult> = emptyList())

data class TMDBVideoResult(val id: String,
                           val type: String,
                           val site: String,
                           val key: String)
