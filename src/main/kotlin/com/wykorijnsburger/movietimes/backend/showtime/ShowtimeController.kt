package com.wykorijnsburger.movietimes.backend.showtime

import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import com.wykorijnsburger.movietimes.backend.utils.InvalidApikeyException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class ShowtimeController(val showtimeService: ShowtimeService, val apiKeysSupplier: APIKeysSupplier) {

    @GetMapping("/app/v1/showtimes")
    fun getAgendaItems(
            @RequestHeader apikey: String,
            limit: Int?
    ): Flux<ShowtimeResponse> {
        if (apikey != apiKeysSupplier.android()) {
            throw InvalidApikeyException()
        }

        val limitWithDefault = limit ?: 50

        return showtimeService.getFromDb()
                .map { it.toResponse() }
    }
}

private fun Showtime.toResponse() = ShowtimeResponse(
        dateTime = dateTime,
        filmTitle = filmTitle,
        filmId = filmId,
        posterUrl = posterUrl,
        location = location
)

data class ShowtimeResponse(
        val dateTime: String,
        val filmTitle: String?,
        val filmId: String,
        val posterUrl: String?,
        val location: String
)