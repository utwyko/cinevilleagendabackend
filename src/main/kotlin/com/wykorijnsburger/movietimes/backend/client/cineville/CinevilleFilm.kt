package com.wykorijnsburger.movietimes.backend.client.cineville


data class CinevilleFilm(val id: String,
                         val year: String?,
                         val title: String,
                         val type: String,
                         val changed: String?,
                         val created: String?,
                         val path: String,
                         val oneliner: String,
                         val still: String?,
                         val poster: String,
                         val keywords: List<String>?,
                         val directors: List<String>?,
                         val countries: List<String>?,
                         val cast: List<String>?,
                         val language: String?,
                         val teaser: String?)
