package com.wykorijnsburger.movietimes.backend.client.tmdb

import com.wykorijnsburger.movietimes.backend.config.APIKeysSupplier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component class TMDBClient(private val apiKeysSupplier: APIKeysSupplier) {
    private val webClient = WebClient.create("https://api.themoviedb.org/3/")

    fun searchMovie(query: String): Mono<TMDBSearchResult> {
        return webClient.get()
                .uri {
                    it
                            .path("search/movie")
                            .queryParam("query", query)
                            .queryParam("api_key", apiKeysSupplier.tmdb())
                            .build()
                }
                .retrieve()
                .bodyToMono(TMDBSearchResult::class.java)
    }

    fun getMovieDetailsWithVideos(id: String): Mono<TMDBDetailsResult> {
        return webClient.get()
                .uri {
                    it
                            .path("movie/{movie_id}")
                            .queryParam("append_to_response", "videos")
                            .queryParam("api_key", apiKeysSupplier.tmdb())
                            .build(id)
                }
                .retrieve()
                .bodyToMono(TMDBDetailsResult::class.java)
    }
}
