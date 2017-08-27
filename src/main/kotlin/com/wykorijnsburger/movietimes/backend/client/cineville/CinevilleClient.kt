package com.wykorijnsburger.movietimes.backend.client.cineville

import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Component
class CinevilleClient(private val apiKeysSupplier: APIKeysSupplier) {
    private val webClient = WebClient.create("http://api.cineville.nl/3/")

    private val encodedAuthKey: String

    init {
        val toBeEncoded = apiKeysSupplier.cineville() + ":" + apiKeysSupplier.cineville()
        encodedAuthKey = Base64Utils.encodeToString(toBeEncoded.toByteArray())
    }

    fun getShowtimes(limit: Int = 10, startDate: LocalDateTime, endDate: LocalDateTime): Flux<CinevilleShowtime> {
        val formattedStartDate = startDate.truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val formattedEndDate = endDate.truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val query = "showtime:[$formattedStartDate TO $formattedEndDate]"

        return webClient.get()
                .uri {
                    it
                            .path("shows.json")
                            .queryParam("rows", limit)
                            .queryParam("query", query)
                            .build()
                }
                .header("Authorization", encodedAuthKey)
                .retrieve()
                .bodyToFlux(CinevilleShowtime::class.java)
    }

    fun getFilms(ids: Set<String>): Flux<CinevilleFilm> {
        val query = if (ids.isEmpty()) {
            null
        } else {
            ids.joinToString(prefix = "id:( ",
                    separator = "+OR+",
                    postfix = ")")
        }

        val limit = if (ids.isEmpty()) null else ids.size

        return webClient.get()
                .uri {
                    it
                            .path("films.json")
                            .queryParam("rows", limit)
                            .queryParam("query", query)
                            .build()
                }
                .header("Authorization", encodedAuthKey)
                .retrieve()
                .bodyToFlux(CinevilleFilm::class.java)
    }
}