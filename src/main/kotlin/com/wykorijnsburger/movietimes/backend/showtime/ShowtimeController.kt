package com.wykorijnsburger.movietimes.backend.showtime

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleShowtime
import com.wykorijnsburger.movietimes.backend.client.tmdb.TMDBClient
import com.wykorijnsburger.movietimes.backend.client.tmdb.TMDBSearchResult
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@Component
class ShowtimeController(val cinevilleClient: CinevilleClient) {

    @GetMapping("/app/v1/showtimes")
    fun getAgendaItems(): List<CinevilleShowtime> {
        return cinevilleClient.getShowTimes(100).collectList().block()
    }
}