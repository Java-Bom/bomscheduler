package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.job.MemoryJobCoordinator
import org.javabom.bomscheduler.job.JobCoordinator
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean

@ConstructorBinding
@ConfigurationProperties(prefix = "bom")
data class BomConfig(val ttl: Long){
    @Bean
    fun jobCoordinator(): JobCoordinator = MemoryJobCoordinator()
}
