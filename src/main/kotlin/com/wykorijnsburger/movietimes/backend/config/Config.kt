package com.wykorijnsburger.movietimes.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@PropertySource("classpath:apikeys.properties")
@EnableScheduling
open class Config {

}