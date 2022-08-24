package org.javabom.bomscheduler.job

import java.time.LocalDateTime

data class Job(
    val name: String,
    val instanceName: String,
    val ttl: Long,
    val version: Int = 0,
    val lastExecutionTime: LocalDateTime = LocalDateTime.now(),
    val instanceExpiredTime: LocalDateTime? = null
)
