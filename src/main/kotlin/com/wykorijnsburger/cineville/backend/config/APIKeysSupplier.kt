package com.wykorijnsburger.cineville.backend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "apikey")
open class APIKeysSupplier() {

    private var tmdb: String

    fun tmdb(): String {
        return this.tmdb
    }

    fun setTmdb(value: String) {
        this.tmdb = value
    }

    init {
        this.tmdb = ""
    }
}