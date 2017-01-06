package com.wykorijnsburger.movietimes.backend.client

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface CinevilleService {
    @GET("shows.json")
    fun getShows(@Query("rows") rows: Int): Observable<List<Show>>
//    fun getShows(@Query("query") query: String, @Query("rows") rows: String): List<Show>
}