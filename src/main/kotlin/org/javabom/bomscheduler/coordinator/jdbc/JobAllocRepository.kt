package org.javabom.bomscheduler.coordinator.jdbc

interface JobAllocRepository {
    fun findByName(name: String): JobAlloc?
    fun save(jobAlloc: JobAlloc)
}