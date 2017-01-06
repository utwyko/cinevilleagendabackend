package com.wykorijnsburger.movietimes.backend.agendaitem

import com.wykorijnsburger.movietimes.backend.client.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.Show
import com.wykorijnsburger.movietimes.backend.movie.tmdb.TMDBClient
import com.wykorijnsburger.movietimes.backend.movie.tmdb.TMDBSearchResult
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Component
class AgendaItemController(val tmdbClient: TMDBClient, val cinevilleClient: CinevilleClient) {

    @GetMapping("/app/v1/agendaitems")
    fun getAgendaItems(): List<Show> {
        return cinevilleClient.getShows(100)
    }
}