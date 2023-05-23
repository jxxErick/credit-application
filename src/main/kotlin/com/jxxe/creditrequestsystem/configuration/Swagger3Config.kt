package com.jxxe.creditrequestsystem.configuration

import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Swagger3Config {

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("jxxecreditrequestsystem-public")
            .pathsToMatch("/api/customers/**", "/api/credits/**")
            .build()
    }
}
