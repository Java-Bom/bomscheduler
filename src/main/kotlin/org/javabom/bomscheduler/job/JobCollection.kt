package org.javabom.bomscheduler.job

class JobCollection(
    private var definedJobs: List<Job> = emptyList()
) {

    init {
        definedJobs.forEach{ println("Register Job ${it.name}")}
    }

    fun getDefinedJobs(): List<Job> = definedJobs

    fun replaceJobs(jobs: List<Job>) {
        definedJobs = jobs
    }
}
