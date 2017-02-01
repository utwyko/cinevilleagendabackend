package com.wykorijnsburger.movietimes.backend.showtime

import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import com.wykorijnsburger.movietimes.backend.utils.toLocalDateTime
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@Component
class ShowtimeController(val showtimeService: ShowtimeService) {

    @GetMapping("/app/v1/showtimes")
    fun getAgendaItems(limit: Int?, startDate: String?, endDate: String?): List<Showtime> {
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
                .collectList()
                .block()
    }
//
//    @GetMapping("/app/v1/update")
//    fun updateAgendaItems(limit: Int?, startDate: String?, endDate: String?) {
//        val limitWithDefault = limit ?: 50
//
//
//        val startDateWithDefault = if (startDate != null) {
//            ISO8601DateFormat().parse(startDate).toLocalDateTime()
//        } else {
//            LocalDateTime.now()
//        }
//
//        val endDateWithDefault = if (endDate != null) {
//            ISO8601DateFormat().parse(endDate).toLocalDateTime()
//        } else {
//            startDateWithDefault.plusDays(7)
//        }
//
//        showtimeService.updateShowtimes(limit = limitWithDefault,
//                startDate = startDateWithDefault,
//                endDate = endDateWithDefault)
//    }
}