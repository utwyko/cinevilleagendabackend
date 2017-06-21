package com.wykorijnsburger.movietimes.backend.showtime

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class ShowtimeRecord(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val dateTime: String,
        val filmTitle: String?,
        val filmId: String,
        val posterUrl: String?,
        val location: String)

fun ShowtimeRecord.toDomain(): Showtime {
    return Showtime(
            dateTime = this.dateTime,
            filmTitle = this.filmTitle,
            filmId = this.filmId,
            posterUrl = this.posterUrl,
            location = this.location
    )
}
