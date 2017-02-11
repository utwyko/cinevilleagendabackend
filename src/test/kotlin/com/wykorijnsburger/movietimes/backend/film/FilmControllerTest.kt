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

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FilmControllerTest {

    @Autowired
    lateinit private var filmRepository: FilmRepository

    @Autowired
    lateinit private var testRestTemplate: TestRestTemplate

    @Test
    fun `should include all film fields`() {
        val randomFilms = EnhancedRandom.randomListOf(5, Film::class.java)
        randomFilms.forEach { filmRepository.save(it) }

        val result = JsonPath.parse(testRestTemplate.getForObject("/app/v1/films", String::class.java))

        result.read<Int>("$.length()") `should equal to` 5
        result.read<Int>("$.[0].cinevilleId") `should not equal` null
        result.read<String>("$.[0].title") `should not equal` null
        result.read<String>("$.[0].posterUrl") `should not equal` null
        result.read<String>("$.[0].stillUrl") `should not equal` null
        result.read<String>("$.[0].trailerUrl") `should not equal` null
        result.read<String>("$.[0].language") `should not equal` null
        result.read<String>("$.[0].oneLiner") `should not equal` null
        result.read<String>("$.[0].year") `should not equal` null
        result.read<String>("$.[0].teaser") `should not equal` null
        result.read<String>("$.[0].runtime") `should not equal` null
    }
}