package org.javabom.bomscheduler.coordinator

fun interface JobCoordinator {
    fun alloc(request: JobAllocRequest): Boolean
}