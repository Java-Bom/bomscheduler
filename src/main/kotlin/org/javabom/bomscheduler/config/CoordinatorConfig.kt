package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.coordinator.JobAllocRepository
import org.javabom.bomscheduler.coordinator.JobCoordinator
import org.javabom.bomscheduler.coordinator.JobManager
import org.javabom.bomscheduler.coordinator.JpaJobCoordinator
import org.javabom.bomscheduler.processor.SingleJobScheduleInterceptor
import org.javabom.bomscheduler.spec.BomScheduleJob
import org.springframework.aop.Advisor
import org.springframework.aop.Pointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(
    value = [
        "org.javabom.bomscheduler.coordinator"
    ]
)
@EnableJpaRepositories(
    value = [
        "org.javabom.bomscheduler.coordinator"
    ]
)
@ComponentScan("org.javabom.bomscheduler.coordinator")
@Configuration
class CoordinatorConfig {

    @Bean
    fun jobManager(): JobManager {
        return JobManager()
    }

    @Bean
    fun jobCoordinator(jobAllocRepository: JobAllocRepository): JobCoordinator {
        return JpaJobCoordinator(jobAllocRepository)
    }

    @Bean
    fun singleJobScheduleInterceptor(): Advisor {
        val interceptor = SingleJobScheduleInterceptor(jobManager())
        val pointcut: Pointcut = AnnotationMatchingPointcut(null, BomScheduleJob::class.java)
        val pointcutAdvisor = DefaultPointcutAdvisor(pointcut, interceptor)
        pointcutAdvisor.order = Ordered.HIGHEST_PRECEDENCE + 1
        return pointcutAdvisor
    }
}