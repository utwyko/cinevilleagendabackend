package com.wykorijnsburger.movietimes.backend.client.tmdb

import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory
import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Component class TMDBClient(val apiKeysSupplier: APIKeysSupplier) {
    private val tmdbService: TMDBService

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        tmdbService = retrofit.create(TMDBService::class.java)
    }

    fun searchMovie(query: String): Mono<TMDBSearchResult> {
        return tmdbService.searchMovies(query, apiKey = apiKeysSupplier.tmdb())
    }

    fun getMovieDetailsWithVideos(id: String): Mono<TMDBDetailsResult> {
        return tmdbService.getMovieDetailsWithVideos(id, apiKey = apiKeysSupplier.tmdb())
    }
}
