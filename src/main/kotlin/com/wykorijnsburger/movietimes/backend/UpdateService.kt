package com.wykorijnsburger.movietimes.backend

import com.wykorijnsburger.movietimes.backend.showtime.ShowtimeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdateService(val showtimeService: ShowtimeService) {

    @Scheduled(fixedDelay = 600000)
    private fun updateShowtimes() {
        showtimeService.updateShowtimes()
    }
}