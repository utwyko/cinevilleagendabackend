package com.wykorijnsburger.movietimes.backend.showtime

data class Showtime(val dateTime: String,
                    val filmTitle: String?,
                    val filmId: String,
                    val posterUrl: String?,
                    val location: String) {
}

