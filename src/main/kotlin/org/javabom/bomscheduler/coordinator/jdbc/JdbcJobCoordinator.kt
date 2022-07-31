package org.javabom.bomscheduler.coordinator.jdbc

import org.javabom.bomscheduler.coordinator.spec.JobAllocRequest
import org.javabom.bomscheduler.coordinator.spec.JobCoordinator
import org.javabom.bomscheduler.coordinator.spec.JobManager
import java.lang.IllegalStateException
import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class JdbcJobCoordinator(
    private val jobAllocRepository: JobAllocRepository,
    private val jobManager: JobManager
) : JobCoordinator {

    //10초마다 갱신 start =now/ end = +30
    override fun alloc(request: JobAllocRequest) {
        // update for
        val alloc: JobAlloc? = jobAllocRepository.findByName(request.jobName)//name == SingleJob
        if (alloc == null) {
            jobAllocRepository.save(JobAlloc(request.allocId, request.startDateTime,request.endDateTime))
        } else if (alloc.allocId == request.allocId) {
            alloc.extendEndDateTime(request.endDateTime) //30초 연장
        } else if (alloc.endDateTime.isBefore(request.startDateTime)){
            alloc.updateJobAlloc(request.allocId, request.startDateTime, request.endDateTime)
            jobManager.updateJobStatus(alloc)
        }else{
            throw IllegalStateException()
        }
    }
}