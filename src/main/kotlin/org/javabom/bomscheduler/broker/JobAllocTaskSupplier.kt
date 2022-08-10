package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask

fun interface JobAllocTaskSupplier {
    fun createJobAllocTasks(): List<JobAllocTask>
}