package com.wykorijnsburger.cineville.backend.movie.tmdb

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.wykorijnsburger.cineville.backend.config.APIKeysSupplier
import org.springframework.stereotype.Component
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Component
open class TMDBClient(val apiKeysSupplier: APIKeysSupplier) {
    private val TMDBAPI: TMDBService

    init {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        TMDBAPI = retrofit.create(TMDBService::class.java)
    }

    fun searchMovie(query: String): TMDBSearchResult? {
        println("apiKeysSupplier = ${apiKeysSupplier.tmdb()}")
        return TMDBAPI.searchMovies(query, apiKey = apiKeysSupplier.tmdb()).blockingFirst()

    }
}
