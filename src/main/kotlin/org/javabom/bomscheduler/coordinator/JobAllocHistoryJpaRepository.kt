package org.javabom.bomscheduler.coordinator

import org.springframework.data.jpa.repository.JpaRepository

interface JobAllocHistoryJpaRepository : JpaRepository<JobAllocHistory, Long>