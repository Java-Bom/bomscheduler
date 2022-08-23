package org.javabom.bomscheduler.coordinator

import org.javabom.bomscheduler.processor.JobAllocRequest

fun interface JobCoordinator {
    fun alloc(request: JobAllocRequest): Boolean
}