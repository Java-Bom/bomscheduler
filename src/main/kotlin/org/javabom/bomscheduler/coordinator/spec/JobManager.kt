package org.javabom.bomscheduler.coordinator.spec

import org.javabom.bomscheduler.coordinator.jdbc.JobAlloc

data class JobManager(var lock: Boolean = true) {

    fun updateJobStatus(alloc: JobAlloc) {
        TODO("Not yet implemented")
    }
}