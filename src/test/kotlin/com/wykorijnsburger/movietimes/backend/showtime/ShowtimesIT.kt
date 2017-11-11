package com.wykorijnsburger.movietimes.backend.showtime

import com.jayway.jsonpath.JsonPath
import io.github.benas.randombeans.api.EnhancedRandom
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should not equal`
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShowtimesIT {

    @Autowired
    lateinit private var showtimesRepository: ShowtimeRepository

    @Autowired
    lateinit private var webTestClient: WebTestClient

    @Test
    fun `should include all film fields`() {
        val randomShowtimes = EnhancedRandom.randomListOf(5, ShowtimeRecord::class.java)
        randomShowtimes.forEach { showtimesRepository.save(it) }

        webTestClient.get()
                .uri("/app/v1/showtimes")
                .header("apikey", "test")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.length()").isEqualTo(5)
                .jsonPath("$.[0].dateTime").exists()
                .jsonPath("$.[0].filmTitle").exists()
                .jsonPath("$.[0].filmId").exists()
                .jsonPath("$.[0].posterUrl").exists()
                .jsonPath("$.[0].location").exists()
    }

    @Test
    fun `Should return unauthorized on invalid apikey`() {
        webTestClient.get()
                .uri("/app/v1/showtimes")
                .header("apikey", "INVALID")
                .exchange()
                .expectStatus().isUnauthorized
    }
}