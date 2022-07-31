package org.javabom.bomscheduler.coordinator.jdbc

import org.springframework.stereotype.Repository

@Repository
class JobAllocJdbcRepositoryImpl : JobAllocRepository {

    override fun findByName(name: String): JobAlloc? {
        TODO("Not yet implemented")
    }

    override fun save(jobAlloc: JobAlloc) {
        TODO("Not yet implemented")
    }
}