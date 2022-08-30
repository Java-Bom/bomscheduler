package org.javabom.bomscheduler.coordinator

import java.time.LocalDateTime

data class JobAllocRequest(
    val allocId: String,
    val jobName: String,
    val startDateTime: LocalDateTime = LocalDateTime.now(),
    val endDateTime: LocalDateTime = startDateTime.plusSeconds(30)
)