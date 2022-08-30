package org.javabom.bomscheduler.broker

fun interface JobAllocTaskSupplier {
    fun createJobAllocTasks(): List<JobAllocTask>
}