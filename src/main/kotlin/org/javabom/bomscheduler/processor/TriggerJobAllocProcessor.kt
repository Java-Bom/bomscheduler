package org.javabom.bomscheduler.processor

import org.javabom.bomscheduler.coordinator.JobManager
import org.javabom.bomscheduler.coordinator.JobTrigger
import org.javabom.bomscheduler.spec.AbstractTriggerJob
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TriggerJobAllocProcessor(
    private val taskScheduler: TaskScheduler,
    private val jobTrigger: JobTrigger,
    private val jobManager: JobManager,
    private val jobs: List<AbstractTriggerJob>,
) {

    @PostConstruct
    fun init() {
        jobs.forEach {
            it.jobTrigger = jobTrigger
            val period = it.getPeriod()
            period.isFixedRate = true
            taskScheduler.schedule(it.getBomScheduleJob(jobManager), period)
        }
    }
}