package com.wykorijnsburger.movietimes.backend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "apikey")
class APIKeysSupplier {

    private var tmdb: String = ""
    private var cineville: String = ""
    private var android: String = ""

    fun tmdb(): String {
        return this.tmdb
    }

    fun setTmdb(value: String) {
        this.tmdb = value
    }

    fun cineville(): String {
        return this.cineville
    }

    fun setCineville(value: String) {
        this.cineville = value
    }

    fun android(): String {
        return this.android
    }

    fun setAndroid(value: String) {
        this.android = value
    }

    init {
        this.tmdb = ""
        this.cineville = ""
        this.android = ""
    }
}