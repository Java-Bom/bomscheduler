package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.job.MemoryJobCoordinator
import org.javabom.bomscheduler.job.JobCoordinator
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class BomConfig{
    @Bean
    fun jobCoordinator(): JobCoordinator = MemoryJobCoordinator()
}
