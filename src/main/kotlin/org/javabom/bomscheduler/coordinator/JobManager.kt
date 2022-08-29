package org.javabom.bomscheduler.coordinator

import org.javabom.bomscheduler.common.logger
import java.util.concurrent.ConcurrentHashMap

class JobManager {
    private val log = logger()
    private val allocStore: MutableMap<String, Boolean> = ConcurrentHashMap<String, Boolean>()

    fun alloc(jobName: String) {
        log.info { "alloc $jobName" }
        allocStore[jobName] = true
    }

    fun free(jobName: String) {
        log.info { "free $jobName" }
        allocStore[jobName] = false
    }

    fun enableAlloc(jobName: String): Boolean {
        return allocStore[jobName] ?: return false
    }
}