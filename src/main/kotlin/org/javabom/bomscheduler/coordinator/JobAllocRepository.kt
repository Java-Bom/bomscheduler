package org.javabom.bomscheduler.coordinator

interface JobAllocRepository {
    fun findByName(name: String): JobAlloc?
    fun save(jobAlloc: JobAlloc)
}