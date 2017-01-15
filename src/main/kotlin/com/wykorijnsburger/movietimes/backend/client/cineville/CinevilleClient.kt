package com.wykorijnsburger.movietimes.backend.client.cineville

import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory
import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import reactor.core.publisher.Flux
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import toFlux
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class CinevilleClient(private val apiKeysSupplier: APIKeysSupplier) {
    private val cinevilleService: CinevilleService

    init {
        val okHttp = OkHttpClient.Builder()
                .addInterceptor {
                    addApikeyInterceptor(it)
                }.build()

        val retrofit = Retrofit.Builder()
                .client(okHttp)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .baseUrl("http://api.cineville.nl/3/")
                .build()

        cinevilleService = retrofit.create(CinevilleService::class.java)
    }

    fun getShowtimes(limit: Int = 10, startDate: LocalDateTime, endDate: LocalDateTime): Flux<CinevilleShowtime> {

        val formattedStartDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val formattedEndDate = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val query = "showtime:[$formattedStartDate TO $formattedEndDate]";

        return cinevilleService.getShowtimes(limit = limit, query = query)
                .flatMap { it.toFlux() }
    }

    fun getFilms(ids: Set<String>): Flux<CinevilleFilm> {
        val idQuery = '(' + ids.joinToString("+OR+") + ')'
        return cinevilleService.getFilms("id:" + idQuery, ids.size).flatMap { it.toFlux() }
    }

    private fun addApikeyInterceptor(it: Interceptor.Chain): Response? {
        val original = it.request()

        val toBeEncoded = apiKeysSupplier.cineville() + ":" + apiKeysSupplier.cineville()
        val encodedAuthKey = Base64Utils.encodeToString(toBeEncoded.toByteArray())
        val request = original.newBuilder()
                .addHeader("Authorization", "Basic $encodedAuthKey")
                .build()

        return it.proceed(request)
    }
}