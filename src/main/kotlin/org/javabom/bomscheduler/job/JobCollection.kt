package org.javabom.bomscheduler.job

/**
 * 정의된 job 에 대한 참조를 가지고 있는 클래스
 */
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
