package com.wykorijnsburger.cineville.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class CinevilleBackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(CinevilleBackendApplication::class.java, *args)
}
