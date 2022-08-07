package org.javabom.bomscheduler.coordinator

import org.javabom.bomscheduler.common.logger
import org.javabom.bomscheduler.processor.JobAllocRequest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

open class SingleJpaJobCoordinator(
    private val jobAllocRepository: JobAllocRepository,
    private val jobManager: JobManager
) : JobCoordinator {

    private val log = logger()

    //10초마다 갱신 start =now/ end = +30
    @Transactional
    override fun alloc(request: JobAllocRequest) {
        // update for
        val startDateTime: LocalDateTime = LocalDateTime.now()
        val endDateTime: LocalDateTime = startDateTime.plusSeconds(30)
        val alloc: JobAlloc? = jobAllocRepository.findByName(request.jobName)//name == SingleJob
        when {
            alloc == null -> {
                jobAllocRepository.save(
                    JobAlloc(
                        jobName = request.jobName,
                        allocId = request.allocId,
                        startDateTime = startDateTime,
                        endDateTime = endDateTime
                    )
                )
                //lock 해제
                jobManager.lock = false
            }
            alloc.allocId == request.allocId -> {
                alloc.endDateTime = endDateTime //30초 연장
                jobManager.lock = false
            }
            alloc.endDateTime.isBefore(startDateTime) -> { //인스턴스 교체
                alloc.updateJobAlloc(request.allocId, startDateTime, endDateTime)
                jobManager.lock = false
                jobAllocRepository.save(alloc)
            }
            else -> {
                log.info { "job alloc wait - $request" }
                jobManager.lock = true
            }
        }
    }
}