package org.javabom.bomscheduler.job

interface JobCoordinator {
    fun alloc(job: Job)
}
