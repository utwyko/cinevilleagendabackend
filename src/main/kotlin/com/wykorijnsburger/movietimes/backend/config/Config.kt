package com.wykorijnsburger.movietimes.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:apikeys.properties", "classpath:security.properties", ignoreResourceNotFound=true)
open class Config