package org.javabom.bomscheduler.coordinator

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class JpaJobTrigger(private val jobAllocRepository: JobAllocRepository) : JobTrigger {

    @Transactional
    override fun start(jobName: String) {
        val alloc: JobAlloc = findJob(jobName)
        alloc.run()
        jobAllocRepository.save(alloc)
    }

    @Transactional
    override fun complete(jobName: String) {
        val alloc: JobAlloc = findJob(jobName)
        alloc.complete()
        jobAllocRepository.save(alloc)
    }

    private fun findJob(jobName: String): JobAlloc {
        return jobAllocRepository.findByName(jobName) ?: throw IllegalArgumentException("등록되지 않은 작업 ($jobName)입니다")
    }
}