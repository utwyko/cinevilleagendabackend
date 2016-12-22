package com.wykorijnsburger.cineville.backend.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@Configuration
@PropertySource("classpath:apikeys.properties")
open class Config {

}