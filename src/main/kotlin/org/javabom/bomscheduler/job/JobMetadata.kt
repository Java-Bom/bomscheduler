package org.javabom.bomscheduler.job

import java.time.LocalDateTime

data class JobMetadata(
    val jobName: String,
    val lastExecutionTime: LocalDateTime,
    val ttl: Long
)
