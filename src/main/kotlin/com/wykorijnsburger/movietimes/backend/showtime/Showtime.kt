package com.wykorijnsburger.movietimes.backend.showtime

data class Showtime(
        val dateTime: String,
        val filmTitle: String?,
        val filmId: String,
        val posterUrl: String?,
        val location: String)

fun Showtime.toRecord(): ShowtimeRecord {
    return ShowtimeRecord(
            dateTime = this.dateTime,
            filmTitle = this.filmTitle,
            filmId = this.filmId,
            posterUrl = this.posterUrl,
            location = this.location
    )
}


