package org.javabom.bomscheduler.coordinator

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class JobAlloc(
    val jobName: String,
    var allocId: String,
    @Enumerated(EnumType.STRING)
    var status: JobAllocStatus = JobAllocStatus.RUNNING,
    var startDateTime: LocalDateTime,
    var endDateTime: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateJobAlloc(allocId: String, startDateTime: LocalDateTime, endDateTime: LocalDateTime) {
        this.allocId = allocId
        this.startDateTime = startDateTime
        this.endDateTime = endDateTime
    }

    fun run() {
        status = JobAllocStatus.RUNNING
        startDateTime = LocalDateTime.now()
        endDateTime = startDateTime.plusSeconds(30)
    }

    fun complete() {
        status = JobAllocStatus.COMPLETE
        endDateTime = LocalDateTime.now()
    }

    fun enableExtend(allocId: String): Boolean {
        return this.allocId == allocId && this.status == JobAllocStatus.RUNNING
    }

    fun enableAlloc(startDateTime: LocalDateTime): Boolean {
        return this.endDateTime.isBefore(startDateTime) && this.status == JobAllocStatus.RUNNING
    }

}