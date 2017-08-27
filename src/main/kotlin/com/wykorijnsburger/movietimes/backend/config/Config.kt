package com.wykorijnsburger.movietimes.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder



@Configuration
@PropertySource("classpath:apikeys.properties", "classpath:security.properties", ignoreResourceNotFound=true)
open class Config {
    @Bean
    fun objectMapperBuilder(): Jackson2ObjectMapperBuilder {
        val builder = Jackson2ObjectMapperBuilder()

        builder.modules(KotlinModule())

        return builder
    }
}