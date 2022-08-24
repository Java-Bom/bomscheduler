package org.javabom.bomscheduler.job
interface JobCoordinator {

    fun getDefinedJob(): List<Job>

    fun removeJobs(jobs: List<Job>)

    fun saveJobs(jobs : List<Job>)

    fun updateJobs(jobs: List<Job>)
}
