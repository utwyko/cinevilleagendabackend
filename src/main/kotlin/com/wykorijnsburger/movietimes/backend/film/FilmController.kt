package com.wykorijnsburger.movietimes.backend.film

import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class FilmController(val filmService: FilmService, val apiKeysSupplier: APIKeysSupplier) {

    @GetMapping("app/v1/films")
    fun getFilms(@RequestHeader apikey: String): Flux<Film>? {

        if (apikey != apiKeysSupplier.android()) {
            // TODO: Status code, error response
            return null
        }

        return filmService.getFilmsFromDb()
    }
}