package com.wykorijnsburger.movietimes.backend.client

import reactor.core.publisher.Mono
import retrofit2.http.GET
import retrofit2.http.Query


interface CinevilleService {
    @GET("shows.json")
    fun getShows(@Query("rows") rows: Int): Mono<List<Show>>
//    fun getShows(@Query("query") query: String, @Query("rows") rows: String): List<Show>
}