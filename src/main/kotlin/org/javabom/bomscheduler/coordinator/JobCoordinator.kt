package org.javabom.bomscheduler.coordinator

import org.javabom.bomscheduler.processor.JobAllocRequest

interface JobCoordinator {
    fun alloc(request: JobAllocRequest)
}