package com.wykorijnsburger.cineville.backend.agendaitem

import com.wykorijnsburger.cineville.backend.movie.tmdb.TMDBClient
import com.wykorijnsburger.cineville.backend.movie.tmdb.TMDBSearchResult
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Component
open class AgendaItemController(val tmdbClient: TMDBClient) {

    @GetMapping("/app/v1/agendaitems")
    fun getAgendaItems(): TMDBSearchResult {
        return tmdbClient.searchMovie("Star Wars")!!
    }
}