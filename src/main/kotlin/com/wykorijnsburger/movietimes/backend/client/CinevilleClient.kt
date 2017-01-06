package com.wykorijnsburger.movietimes.backend.client

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import okhttp3.OkHttpClient
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

@Component
open class CinevilleClient(val apiKeysSupplier: APIKeysSupplier) {
    private val cinevilleService: CinevilleService

    init {
        val okHttp = OkHttpClient.Builder()
                .addInterceptor {
                    val original = it.request()

                    println(apiKeysSupplier.cineville())

                    val toBeEncoded = apiKeysSupplier.cineville() + ":" + apiKeysSupplier.cineville()
                    val encodedAuthKey = Base64Utils.encodeToString(toBeEncoded.toByteArray())
                    val request = original.newBuilder()
                            .addHeader("Authorization", "Basic $encodedAuthKey")
                            .build()

                    it.proceed(request)
                }.build()

        val retrofit = Retrofit.Builder()
                .client(okHttp)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://api.cineville.nl/3/")
                .build()

        cinevilleService = retrofit.create(CinevilleService::class.java)
    }

    fun getShows(limit: Int): List<Show> {
        return cinevilleService.getShows(limit).blockingFirst()
    }
}