package com.wykorijnsburger.cineville.backend.agendaitem

import java.util.*

data class AgendaItem(
        val cinevilleId: String,
        val cinevilleFilmId: String,
        val title: String,
        val theaterName: String,
        val dateTime: Date,
        val posterUrl: String
)