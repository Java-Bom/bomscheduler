package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask
import org.springframework.stereotype.Component

@Component
class SingleJobAllocTaskSupplier : JobAllocTaskSupplier {

    override fun createJobAllocTask(): JobAllocTask {
        return JobAllocTask(
            jobName = "DEFAULT_SCHEDULER",
            delayInMilliseconds = 10000L
        )
    }
}