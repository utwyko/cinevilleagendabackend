package com.wykorijnsburger.movietimes.backend.client.tmdb

import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory
import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Component
open class TMDBClient(val apiKeysSupplier: APIKeysSupplier) {
    private val tmdbService: TMDBService

    init {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        tmdbService = retrofit.create(TMDBService::class.java)
    }

    fun searchMovie(query: String): Mono<TMDBSearchResult> {
        return tmdbService.searchMovies(query, apiKey = apiKeysSupplier.tmdb())
    }

    fun getVideos(id: String): Mono<TMDBVideoResult> {
        return tmdbService.getVideos(id, apiKey = apiKeysSupplier.tmdb())
    }
}
