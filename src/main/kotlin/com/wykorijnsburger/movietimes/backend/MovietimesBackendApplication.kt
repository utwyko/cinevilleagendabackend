package com.wykorijnsburger.movietimes.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MovietimesBackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(MovietimesBackendApplication::class.java, *args)
}
