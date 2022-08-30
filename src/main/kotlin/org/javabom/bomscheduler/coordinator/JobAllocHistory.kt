package org.javabom.bomscheduler.coordinator

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class JobAllocHistory(jobAlloc: JobAlloc) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val jobName: String = jobAlloc.jobName

    @Enumerated(EnumType.STRING)
    var status: JobAllocStatus = jobAlloc.status
    val allocId: String = jobAlloc.allocId
    val startDateTime: LocalDateTime = jobAlloc.startDateTime
    val endDateTime: LocalDateTime = jobAlloc.endDateTime
}