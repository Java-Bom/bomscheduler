package org.javabom.bomscheduler.coordinator.config

import org.javabom.bomscheduler.coordinator.jdbc.JdbcJobCoordinator
import org.javabom.bomscheduler.coordinator.spec.JobCoordinator
import org.javabom.bomscheduler.coordinator.spec.JobManager
import org.javabom.bomscheduler.coordinator.spec.SingleJob
import org.springframework.aop.Advisor
import org.springframework.aop.Pointcut
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class CoordinatorConfig {

    @Bean
    fun jobManager(): JobManager {
        return JobManager()
    }

    @Bean
    fun jobCoordinator(): JobCoordinator {
        return JdbcJobCoordinator(jobManager())
    }

    @Bean
    fun singleJobScheduleInterceptor(): Advisor {
        val interceptor = SingleJobScheduleInterceptor(jobManager())
        val pointcut: Pointcut = AnnotationMatchingPointcut(SingleJob::class.java)
        val pointcutAdvisor = DefaultPointcutAdvisor(pointcut, interceptor)
        pointcutAdvisor.order = Ordered.HIGHEST_PRECEDENCE + 1
        return pointcutAdvisor
    }
}