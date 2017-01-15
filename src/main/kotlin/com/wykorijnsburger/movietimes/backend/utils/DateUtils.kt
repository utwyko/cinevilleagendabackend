package com.wykorijnsburger.movietimes.backend.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}



