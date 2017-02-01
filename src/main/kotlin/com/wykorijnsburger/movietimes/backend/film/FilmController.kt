package com.wykorijnsburger.movietimes.backend.film

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FilmController(val filmService: FilmService) {

    //    @GetMapping("app/v1/films")
//    fun getFilms(@RequestParam id: Set<String>): List<Film>? {
//        return filmService.getFilms(id)
//                .collectList()
//                .block()
//    }
//
    @GetMapping("app/v1/films")
    fun getFilms(): List<Film>? {
        return filmService.getFilmsFromDb()
                .collectList()
                .block()
    }
}