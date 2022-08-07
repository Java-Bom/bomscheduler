package org.javabom.bomscheduler.coordinator

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class JobAlloc(
    val jobName: String,
    var allocId: String,
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

}