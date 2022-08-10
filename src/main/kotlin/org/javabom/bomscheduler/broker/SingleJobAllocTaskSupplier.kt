package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask
import org.springframework.stereotype.Component

@Component
class SingleJobAllocTaskSupplier : JobAllocTaskSupplier {

    override fun createJobAllocTasks(): List<JobAllocTask> {
        return listOf(
            JobAllocTask(
                jobName = "DEFAULT_SCHEDULER",
                delayInMilliseconds = 10000
            )
        )
    }
}