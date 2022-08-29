package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask
import org.javabom.bomscheduler.spec.AbstractTriggerJob
import org.springframework.stereotype.Component

@Component
class TriggerJobAllocTaskSupplier(bomJobs: List<AbstractTriggerJob>) : JobAllocTaskSupplier {
    private var triggerJobStore: Set<String> = bomJobs.map { it.javaClass.name }.toSet()

    override fun createJobAllocTasks(): List<JobAllocTask> {
        return triggerJobStore
            .map { JobAllocTask(jobName = it, delayInMilliseconds = 60_000) }
            .toList()
    }
}