package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask
import org.javabom.bomscheduler.spec.BomScheduleJob
import org.springframework.stereotype.Component

@Component
class SingleJobAllocTaskSupplier : JobAllocTaskSupplier {

    override fun createJobAllocTasks(): List<JobAllocTask> {
        return listOf(
            JobAllocTask(
                jobName = BomScheduleJob.DEFAULT_JOB,
                delayInMilliseconds = 10_000
            )
        )
    }
}