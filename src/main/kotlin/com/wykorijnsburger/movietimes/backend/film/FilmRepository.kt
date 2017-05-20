package com.wykorijnsburger.movietimes.backend.film

import org.springframework.data.repository.CrudRepository

interface FilmRepository : CrudRepository<FilmRecord, Long> {

}