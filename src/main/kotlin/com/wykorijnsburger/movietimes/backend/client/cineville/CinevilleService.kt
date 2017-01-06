package com.wykorijnsburger.movietimes.backend.client.cineville

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import retrofit2.http.GET
import retrofit2.http.Query


interface CinevilleService {
    @GET("shows.json")
    fun getShowtimes(@Query("rows") rows: Int): Mono<List<CinevilleShowtime>>
//    fun getShowTimes(@Query("query") query: String, @Query("rows") rows: String): List<CinevilleShowtime>
}