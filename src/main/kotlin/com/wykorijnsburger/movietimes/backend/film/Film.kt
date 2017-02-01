package com.wykorijnsburger.movietimes.backend.film

import javax.persistence.*


@Entity
data class Film(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val title: String,
        val posterUrl: String? = null,
        val stillUrl: String? = null,
        val trailerUrl: String? = null,
        @ElementCollection
        val directors: List<String> = emptyList(),
        @ElementCollection
        val cast: List<String> = emptyList(),
        val language: String? = null,
        @Column(columnDefinition="text")
        val oneLiner: String? = null,
        val year: String? = null,
        val cinevilleId: String? = null,
        @Column(columnDefinition="text")
        val teaser: String? = null
) {
    fun isEmpty(): Boolean {
        return this == emptyFilm()
    }
}

fun emptyFilm(): Film {
    return Film(title = "", posterUrl = "")
}