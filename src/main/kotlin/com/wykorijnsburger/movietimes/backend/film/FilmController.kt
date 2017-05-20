package com.wykorijnsburger.movietimes.backend.film

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class FilmController(val filmService: FilmService) {

    @GetMapping("app/v1/films")
    fun getFilms(): Flux<Film>? {
        return filmService.getFilmsFromDb()
    }
}