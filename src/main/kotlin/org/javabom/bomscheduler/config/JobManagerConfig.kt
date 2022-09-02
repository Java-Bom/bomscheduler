package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.job.JobCollection
import org.javabom.bomscheduler.job.JobCoordinator
import org.javabom.bomscheduler.job.JobManager
import org.javabom.bomscheduler.schedule.BomSchedule
import org.javabom.bomscheduler.schedule.BomScheduleInterceptor
import org.javabom.bomscheduler.task.TaskManager
import org.javabom.bomscheduler.task.TaskRunner
import org.springframework.aop.Advisor
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
class JobManagerConfig {

    @Bean
    fun jobManager(jobCoordinator: JobCoordinator, jobCollection: JobCollection): JobManager {
        return JobManager(jobCoordinator, jobCollection)
    }

    @Bean
    fun taskRunner(): TaskRunner {
        return TaskRunner()
    }

    @Bean
    fun TaskManager(
        jobManager: JobManager,
        jobCollection: JobCollection
    ): TaskManager {
        return TaskManager(jobManager, jobCollection, taskRunner())
    }

    @Bean
    fun bomScheduleInterceptor(): Advisor {
        val interceptor = BomScheduleInterceptor(taskRunner())
        val pointcut = AnnotationMatchingPointcut(BomSchedule::class.java)
        return DefaultPointcutAdvisor(pointcut, interceptor)
    }
}