package org.javabom.bomscheduler.coordinator

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType

interface JobAllocJpaRepository : JpaRepository<JobAlloc, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByJobName(name: String): JobAlloc?

}