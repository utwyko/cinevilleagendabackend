package com.wykorijnsburger.movietimes.backend.showtime

import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import com.wykorijnsburger.movietimes.backend.utils.toLocalDateTime
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@RestController
@Component
class ShowtimeController(val showtimeService: ShowtimeService, val apiKeysSupplier: APIKeysSupplier) {

    @GetMapping("/app/v1/showtimes")
    fun getAgendaItems(@RequestHeader apikey: String, limit: Int?, startDate: String?, endDate: String?): Flux<Showtime>? {
        if (apikey != apiKeysSupplier.android()) {
            // TODO: Status code, error response
            return null
        }

        val limitWithDefault = limit ?: 50

        val startDateWithDefault = if (startDate != null) {
            ISO8601DateFormat().parse(startDate).toLocalDateTime()
        } else {
            LocalDateTime.now()
        }

        val endDateWithDefault = if (endDate != null) {
            ISO8601DateFormat().parse(endDate).toLocalDateTime()
        } else {
            startDateWithDefault.plusDays(7)
        }

        return showtimeService.getFromDb()
    }
}