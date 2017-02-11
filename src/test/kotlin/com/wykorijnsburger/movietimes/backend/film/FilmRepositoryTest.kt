package com.wykorijnsburger.movietimes.backend.film

import io.github.benas.randombeans.api.EnhancedRandom.randomListOf
import org.amshove.kluent.`should equal`
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner



@RunWith(SpringRunner::class)
@DataJpaTest
class FilmRepositoryTest {

    @Autowired
    lateinit private var entityManager: TestEntityManager

    @Autowired
    lateinit private var filmRepository: FilmRepository

    @Test
    fun `should save and return films unchanged`() {
        val randomFilms = randomListOf(5, Film::class.java)
        randomFilms.forEach { entityManager.persist(it) }

        val result = filmRepository.findAll()

        result.toSet() `should equal` randomFilms.toSet()
    }
}