package org.javabom.bomscheduler.job

interface JobCoordinator {
    fun alloc(job: Job)

    fun start(job: Job)
    fun end(job: Job)
}
