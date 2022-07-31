package org.javabom.bomscheduler.coordinator.spec

import java.time.LocalDateTime

data class JobAllocRequest(
    val allocId: String,
    val jobName: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)