package org.javabom.bomscheduler.coordinator

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class JobAllocHistory(jobAlloc: JobAlloc) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val jobName: String = jobAlloc.jobName
    val allocId: String = jobAlloc.allocId
    val startDateTime: LocalDateTime = jobAlloc.startDateTime
    val endDateTime: LocalDateTime = jobAlloc.endDateTime
}