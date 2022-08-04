package org.javabom.bomscheduler.coordinator.spec

interface JobCoordinator {
    fun createDelayJobAlloc(): JobAllocTask
    fun alloc(request: JobAllocRequest)
}