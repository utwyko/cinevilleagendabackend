package com.wykorijnsburger.movietimes.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@Profile("!test")
@EnableScheduling
class SchedulingConfiguration