package org.javabom.bomscheduler.coordinator

import org.javabom.bomscheduler.common.logger
import org.javabom.bomscheduler.processor.JobAllocRequest
import org.springframework.transaction.annotation.Transactional

open class JpaJobCoordinator(
    private val jobAllocRepository: JobAllocRepository,
) : JobCoordinator {

    private val log = logger()

    //10초마다 갱신 start =now/ end = +30
    @Transactional
    override fun alloc(request: JobAllocRequest): Boolean {
        // update for
        val alloc: JobAlloc? = jobAllocRepository.findByName(request.jobName)//name == SingleJob
        when {
            alloc == null -> {
                jobAllocRepository.save(
                    JobAlloc(
                        jobName = request.jobName,
                        allocId = request.allocId,
                        startDateTime = request.startDateTime,
                        endDateTime = request.endDateTime
                    )
                )
                //lock 해제
                return true
            }

            alloc.enableExtend(request.allocId) -> {
                alloc.endDateTime = request.endDateTime //30초 연장
                return true
            }

            alloc.enableAlloc(request.startDateTime) -> { //인스턴스 교체
                alloc.updateJobAlloc(request.allocId, request.startDateTime, request.endDateTime)
                jobAllocRepository.save(alloc)
                return true
            }

            else -> {
                log.info { "job alloc wait - ${request.allocId}" }
                return false
            }
        }
    }
}