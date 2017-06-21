package com.wykorijnsburger.movietimes.backend.utils

import java.util.*

fun <T : Any> Optional<T>.orNull(): T? {
    if (this.isPresent) {
        return this.get()
    }

    return null
}
