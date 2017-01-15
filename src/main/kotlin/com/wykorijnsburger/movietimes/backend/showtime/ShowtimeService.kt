package com.wykorijnsburger.movietimes.backend.showtime

import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleClient
import com.wykorijnsburger.movietimes.backend.client.cineville.CinevilleShowtime
import com.wykorijnsburger.movietimes.backend.film.Film
import com.wykorijnsburger.movietimes.backend.film.FilmService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Service
class ShowtimeService(private val cinevilleClient: CinevilleClient, private val filmService: FilmService) {

    fun getShowtimes(startDate: LocalDateTime,
                     endDate: LocalDateTime,
                     limit: Int = 10): Flux<Showtime> {
        val showtimes = cinevilleClient.getShowtimes(limit, startDate, endDate)

        val films = showtimes.collectList()
                .map { it.map { it.film_id } }
                .flatMap { filmService.getFilms(it) }

        return Flux.zip(showtimes, films)
                .map { compose(it.t1, it.t2) }
    }


    fun compose(cinevilleShowtime: CinevilleShowtime, film: Film): Showtime {
        val nullableFilm: Film? = if (film.isEmpty()) null else film

        return Showtime(dateTime = cinevilleShowtime.showtime,
                filmTitle = nullableFilm?.title,
                posterUrl = nullableFilm?.posterUrl,
                filmId = cinevilleShowtime.film_id,
                location = cinevilleShowtime.location)
    }
}