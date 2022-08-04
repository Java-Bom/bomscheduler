package org.javabom.bomscheduler.coordinator.spec

data class JobAllocRequest(
    val allocId: String,
    val jobName: String
)