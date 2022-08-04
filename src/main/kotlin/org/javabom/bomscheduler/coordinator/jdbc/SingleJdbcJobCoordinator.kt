package org.javabom.bomscheduler.coordinator.jdbc

import org.javabom.bomscheduler.coordinator.spec.JobAllocRequest
import org.javabom.bomscheduler.coordinator.spec.JobAllocTask
import org.javabom.bomscheduler.coordinator.spec.JobCoordinator
import org.javabom.bomscheduler.coordinator.spec.JobManager
import java.time.LocalDateTime

class SingleJdbcJobCoordinator(
    private val jobAllocRepository: JobAllocRepository,
    private val jobManager: JobManager
) : JobCoordinator {

    override fun createDelayJobAlloc(): JobAllocTask {
        return JobAllocTask(
            jobName = "DEFAULT_SCHEDULER",
            delayInMilliseconds = 10000L
        )
    }

    //10초마다 갱신 start =now/ end = +30
    override fun alloc(request: JobAllocRequest) {
        // update for
        val startDateTime: LocalDateTime = LocalDateTime.now()
        val endDateTime: LocalDateTime = startDateTime.plusSeconds(30)
        val alloc: JobAlloc? = jobAllocRepository.findByName(request.jobName)//name == SingleJob
        when {
            alloc == null -> {
                jobAllocRepository.save(JobAlloc(request.allocId, startDateTime, endDateTime))
                //lock 해제
                jobManager.lock = false
            }
            alloc.allocId == request.allocId -> {
                alloc.extendEndDateTime(endDateTime) //30초 연장
            }
            alloc.endDateTime.isBefore(startDateTime) -> { //인스턴스 교체
                alloc.updateJobAlloc(request.allocId, startDateTime, endDateTime)
                jobManager.lock = true
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }
}