package org.javabom.bomscheduler.coordinator

import org.springframework.stereotype.Repository

@Repository
class JobAllocRepositoryImpl(
    private val jobAllocJpaRepository: JobAllocJpaRepository,
    private val jobAllocHistoryJpaRepository: JobAllocHistoryJpaRepository
) : JobAllocRepository {

    override fun findByName(name: String): JobAlloc? {
        return jobAllocJpaRepository.findByJobName(name)
    }

    override fun save(jobAlloc: JobAlloc) {
        jobAllocJpaRepository.save(jobAlloc)
        jobAllocHistoryJpaRepository.save(JobAllocHistory(jobAlloc))
    }
}