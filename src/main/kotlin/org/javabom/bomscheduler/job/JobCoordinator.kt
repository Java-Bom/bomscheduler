package org.javabom.bomscheduler.job

import org.springframework.stereotype.Service


@Service
interface JobCoordinator {

    fun getDefinedJob(): List<Job>

    fun removeJobs(jobs: List<Job>)

    fun saveJobs(jobs : List<Job>)

    fun updateJobs(jobs: List<Job>)
}
