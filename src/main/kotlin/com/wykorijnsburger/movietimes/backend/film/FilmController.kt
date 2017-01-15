package com.wykorijnsburger.movietimes.backend.film

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleFilm
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FilmController(val cinevilleClient: CinevilleClient) {

    @GetMapping("app/v1/films")
    fun getFilms(ids: Set<String>): List<CinevilleFilm> {
        return cinevilleClient.getFilms(ids).collectList().block()
    }
}