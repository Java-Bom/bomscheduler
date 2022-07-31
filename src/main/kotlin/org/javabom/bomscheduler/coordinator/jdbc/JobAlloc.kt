package org.javabom.bomscheduler.coordinator.jdbc

import java.time.LocalDateTime

class JobAlloc(
    val allocId: String,
    val startDateTime: LocalDateTime,
    var endDateTime: LocalDateTime,
) {
    fun extendEndDateTime(endDateTime: LocalDateTime) {
        TODO("Not yet implemented")
    }

    fun updateJobAlloc(allocId: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime) {

    }

}