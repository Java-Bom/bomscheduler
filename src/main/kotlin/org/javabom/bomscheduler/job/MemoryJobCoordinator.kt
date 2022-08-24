package org.javabom.bomscheduler.job

class MemoryJobCoordinator : JobCoordinator {

    val sampleSavedJobs: MutableList<Job> = mutableListOf(Job("JOB_HELLO", "RANDOM", 3000L))

    override fun getDefinedJob(): List<Job> {
        return sampleSavedJobs
    }

    override fun removeJobs(jobs: List<Job>) {
        jobs.forEach { sampleSavedJobs.remove(it) }
    }

    override fun saveJobs(jobs: List<Job>) {
        jobs.forEach { sampleSavedJobs.add(it) }
    }

    override fun updateJobs(jobs: List<Job>) {
        jobs.forEach { updateJob(it) }
    }

    private fun updateJob(newJob: Job) {
        val findJobs = sampleSavedJobs.find { it.name == newJob.name }
        val index = sampleSavedJobs.indexOf(findJobs)
        sampleSavedJobs.set(index, newJob)
    }

}
