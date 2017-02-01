package com.wykorijnsburger.movietimes.backend.film

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleFilm
import com.wykorijnsburger.movietimes.backend.client.cineville.emptyFilm
import com.wykorijnsburger.movietimes.backend.client.tmdb.TMDBClient
import com.wykorijnsburger.movietimes.backend.client.tmdb.TMDBVideoResult
import com.wykorijnsburger.movietimes.backend.showtime.ShowtimeRecord
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import toFlux
import toMono

@Service
class FilmService(private val cinevilleClient: CinevilleClient,
                  private val tmdbClient: TMDBClient,
                  private val filmRepository: FilmRepository) {

    fun updateFilms(showtimes: List<ShowtimeRecord>) {
        val filmIds = showtimes
                .map { it.filmId }
                .toSet()

        getFilms(filmIds)
                .subscribe { filmRepository.save(it) }
    }

    fun getFilmsFromDb(): Flux<Film> {
        return filmRepository.findAll()
                .toFlux()
    }

    fun getCinevilleFilms(ids: List<String>): Flux<CinevilleFilm>? {
        val cinevilleFilms = cinevilleClient.getFilms(ids.toSet())
                .cache()

        val paddedFilms = cinevilleFilms.collectList()
                .map {
                    val filmMap = it.associateBy({ it.id }, { it })
                    ids.map { id -> filmMap[id] ?: emptyFilm() }
                }
                .flatMap { it.toFlux() }

        return paddedFilms
    }

    private fun toFilm(it: CinevilleFilm): Film {
        return Film(title = it.title,
                language = it.language,
                posterUrl = it.poster,
                year = it.year,
                directors = it.directors.orEmpty(),
                cast = it.cast.orEmpty(),
                oneLiner = it.oneliner,
                cinevilleId = it.id,
                teaser = it.teaser,
                stillUrl = it.still)
    }

    fun getFilms(ids: Set<String>): Flux<Film> {
        val cinevilleFilms = cinevilleClient.getFilms(ids = ids)

        val tmdbFilms: Flux<TMDBVideoResult> = cinevilleFilms.map { it.title }
                .delayMillis(250)
                .flatMap { tmdbClient.searchMovie(it) }
                .flatMap {
                    val filmId = it.results.firstOrNull()?.id
                    if (filmId != null) {
                        tmdbClient.getVideos(filmId)
                    }

                    TMDBVideoResult("test", "test", "test").toMono()
                }

        return Flux.zip(cinevilleFilms, tmdbFilms)
                .map {
                    Film(title = it.t1.title,
                            language = it.t1.language,
                            posterUrl = it.t1.poster,
                            year = it.t1.year,
                            directors = it.t1.directors.orEmpty(),
                            cast = it.t1.cast.orEmpty(),
                            oneLiner = it.t1.oneliner,
                            cinevilleId = it.t1.id,
                            teaser = it.t1.teaser,
                            stillUrl = it.t1.still)
                }
    }
}