package com.wykorijnsburger.movietimes.backend.film

import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import com.wykorijnsburger.movietimes.backend.utils.InvalidApikeyException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class FilmController(val filmService: FilmService, val apiKeysSupplier: APIKeysSupplier) {

    @GetMapping("app/v1/films")
    fun getFilms(@RequestHeader apikey: String): Flux<FilmResponse> {

        if (apikey != apiKeysSupplier.android()) {
            throw InvalidApikeyException()
        }

        return filmService.getFilmsFromDb()
                .map { it.toResponse() }
    }
}

private fun Film.toResponse() = FilmResponse(
        cinevilleId = cinevilleId,
        title = title,
        posterUrl = posterUrl,
        stillUrl = stillUrl,
        trailerUrl = trailerUrl,
        directors = directors,
        cast = cast,
        language = language,
        oneLiner = oneLiner,
        year = year,
        teaser = teaser,
        runtime = runtime
)

data class FilmResponse(
        val cinevilleId: Long,
        val title: String,
        val posterUrl: String? = null,
        val stillUrl: String? = null,
        val trailerUrl: String? = null,
        val directors: List<String>,
        val cast: List<String>,
        val language: String? = null,
        val oneLiner: String? = null,
        val year: String? = null,
        val teaser: String? = null,
        val runtime: Int? = null
)