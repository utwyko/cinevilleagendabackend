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

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShowtimesIT {

    @Autowired
    lateinit private var showtimesRepository: ShowtimeRepository

    @Autowired
    lateinit private var testRestTemplate: TestRestTemplate

    @Test
    fun `should include all film fields`() {
        val randomShowtimes = EnhancedRandom.randomListOf(5, ShowtimeRecord::class.java)
        randomShowtimes.forEach { showtimesRepository.save(it) }

        val result = JsonPath.parse(testRestTemplate.getForObject("/app/v1/showtimes", String::class.java))

        result.read<Int>("$.length()") `should equal to` 5
        result.read<Int>("$.[0].dateTime") `should not equal` null
        result.read<String>("$.[0].filmTitle") `should not equal` null
        result.read<String>("$.[0].filmId") `should not equal` null
        result.read<String>("$.[0].posterUrl") `should not equal` null
        result.read<String>("$.[0].location") `should not equal` null
    }
}