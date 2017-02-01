package com.wykorijnsburger.movietimes.backend.showtime

import org.springframework.data.repository.CrudRepository

interface ShowtimeRepository : CrudRepository<ShowtimeRecord, Long> {
    fun findById(id: String) : ShowtimeRecord
}


