package com.wykorijnsburger.movietimes.backend.film

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleFilm
import com.wykorijnsburger.movietimes.backend.client.cineville.emptyFilm
import com.wykorijnsburger.movietimes.backend.client.tmdb.TMDBClient
import com.wykorijnsburger.movietimes.backend.client.tmdb.emptyTMDBDetails
import com.wykorijnsburger.movietimes.backend.showtime.ShowtimeRecord
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

@Service
class FilmService(private val cinevilleClient: CinevilleClient,
                  private val tmdbClient: TMDBClient,
                  private val filmRepository: FilmRepository) {

    private val logger = KotlinLogging.logger {}

    fun updateFilms(showtimes: List<ShowtimeRecord>) {
        val filmIds = showtimes
                .map { it.filmId }
                .toSet()

        getFilms(filmIds)
                .doOnError { logger.error(it) { "Error retrieving films: $it"}}
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

    private fun getFilms(ids: Set<String>): Flux<Film> {
        val cinevilleFilms = cinevilleClient.getFilms(ids = ids)

        val tmdbFilms = cinevilleFilms.map { it.title }
                .delayMillis(500)
                .flatMap { tmdbClient.searchMovie(it) }
                .flatMap {
                    val filmId = it.results.firstOrNull()?.id
                    if (filmId != null) {
                        tmdbClient.getMovieDetailsWithVideos(filmId)
                    } else {
                        emptyTMDBDetails.toMono()
                    }
                }

        return Flux.zip(cinevilleFilms, tmdbFilms)
                .map { compose(it.t1, it.t2) }
    }
}