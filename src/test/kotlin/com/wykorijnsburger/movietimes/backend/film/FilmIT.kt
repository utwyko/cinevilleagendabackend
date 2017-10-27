package com.wykorijnsburger.movietimes.backend.film

import com.jayway.jsonpath.JsonPath
import io.github.benas.randombeans.api.EnhancedRandom
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should not equal`
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FilmIT {

    @Autowired
    lateinit private var filmRepository: FilmRepository

    @Autowired
    lateinit private var webTestClient: WebTestClient

    @Test
    fun `should include all film fields`() {
        val randomFilms = EnhancedRandom.randomListOf(5, FilmRecord::class.java)
        randomFilms.forEach { filmRepository.save(it) }

        webTestClient.get()
                .uri("/app/v1/films")
                .header("apikey", "test")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.length()").isEqualTo(5)
                .jsonPath("$.[0].cinevilleId").exists()
                .jsonPath("$.[0].title").exists()
                .jsonPath("$.[0].posterUrl").exists()
                .jsonPath("$.[0].stillUrl").exists()
                .jsonPath("$.[0].trailerUrl").exists()
                .jsonPath("$.[0].language").exists()
                .jsonPath("$.[0].oneLiner").exists()
                .jsonPath("$.[0].year").exists()
                .jsonPath("$.[0].teaser").exists()
                .jsonPath("$.[0].runtime").exists()
    }
}