package com.example.spring_security.configuration.auditing

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class AppConfiguration {

    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return SpringSecurityAuditorAware()
    }
}
