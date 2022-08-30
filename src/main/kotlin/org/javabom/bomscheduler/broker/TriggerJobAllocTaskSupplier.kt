package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask
import org.springframework.stereotype.Component

@Component
class TriggerJobAllocTaskSupplier(private val creator: List<TriggerJobAllocTaskCreator>) : JobAllocTaskSupplier {

    override fun createJobAllocTasks(): List<JobAllocTask> {
        return creator
            .map { it.jobAllocTask() }
            .toList()
    }
}