package com.wykorijnsburger.movietimes.backend.showtime

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleShowtime
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ShowtimeService(val cinevilleClient: CinevilleClient) {

    fun getShowtimes(query: String, limit: Int): Flux<CinevilleShowtime> {
        return cinevilleClient.getShowTimes(limit)
    }
}