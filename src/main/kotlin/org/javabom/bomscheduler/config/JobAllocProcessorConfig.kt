package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.broker.JobAllocTaskBroker
import org.javabom.bomscheduler.broker.JobAllocTaskSupplier
import org.javabom.bomscheduler.coordinator.JobCoordinator
import org.javabom.bomscheduler.processor.JobAllocProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.task.TaskSchedulerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
@Import(CoordinatorConfig::class)
@ComponentScan("org.javabom.bomscheduler.broker")
@ConditionalOnProperty(name = ["bomscheduler.processor.mode"], havingValue = "on")
class JobAllocProcessorConfig {

    @Bean
    fun jobAllocTaskBroker(lists: List<JobAllocTaskSupplier>): JobAllocTaskBroker {
        return JobAllocTaskBroker(lists)
    }

    @Bean
    fun jobProcessor(
        jobCoordinator: JobCoordinator,
        jobAllocTaskBroker: JobAllocTaskBroker
    ): JobAllocProcessor {
        return JobAllocProcessor(
            jobCoordinator = jobCoordinator,
            jobAllocTaskBroker = jobAllocTaskBroker
        )
    }

    @Bean
    fun taskSchedulerCustomizer(lists: List<JobAllocTaskSupplier>): TaskSchedulerCustomizer {
        return TaskSchedulerCustomizer { taskScheduler: ThreadPoolTaskScheduler ->
            taskScheduler.setAwaitTerminationSeconds(60)
            taskScheduler.setWaitForTasksToCompleteOnShutdown(true)
        }
    }
}