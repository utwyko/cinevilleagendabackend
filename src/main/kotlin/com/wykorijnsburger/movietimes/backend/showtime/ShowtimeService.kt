package com.wykorijnsburger.movietimes.backend.showtime

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleFilm
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleShowtime
import com.wykorijnsburger.movietimes.backend.film.FilmService
import com.wykorijnsburger.movietimes.backend.utils.orNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import java.time.LocalDateTime

@Service
class ShowtimeService(private val cinevilleClient: CinevilleClient,
                      private val filmService: FilmService,
                      private val showtimeRepository: ShowtimeRepository) {

    fun getFromDb(): Flux<Showtime> {
        return showtimeRepository.findAll()
                .toFlux()
                .map { it.toDomain() }
    }

    fun updateShowtimes() {
        getShowtimes(LocalDateTime.now(), LocalDateTime.now().plusDays(8))
                .map { it.toRecord() }
                .collectList()
                .subscribe {
                    showtimeRepository.deleteAll()
                    showtimeRepository.saveAll(it)
                    filmService.updateFilms(it)
                }
    }

    private fun getShowtimes(startDate: LocalDateTime,
                             endDate: LocalDateTime,
                             limit: Int = 10000): Flux<Showtime> {
        val showtimes = cinevilleClient.getShowtimes(limit, startDate, endDate)

        val films = showtimes
                .map { it.film_id }
                .collectList()
                .flatMapMany { filmService.getCinevilleFilms(it) }

        return Flux.zip(showtimes, films)
                .map { compose(it.t1, it.t2.orNull()) }
                .filter { it.filmTitle != null }
    }

    private fun compose(cinevilleShowtime: CinevilleShowtime, film: CinevilleFilm?): Showtime {
        return Showtime(dateTime = cinevilleShowtime.showtime,
                filmTitle = film?.title,
                posterUrl = film?.poster,
                filmId = cinevilleShowtime.film_id,
                location = cinevilleShowtime.location)
    }
}