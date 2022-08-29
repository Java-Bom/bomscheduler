package org.javabom.bomscheduler.spec

import org.javabom.bomscheduler.coordinator.JobManager
import org.javabom.bomscheduler.coordinator.JobTrigger
import org.springframework.scheduling.support.PeriodicTrigger
import java.util.concurrent.TimeUnit

abstract class AbstractTriggerJob(private val periodSeconds: Long) {
    internal lateinit var jobManager: JobManager
    internal lateinit var jobTrigger: JobTrigger

    internal fun getPeriod(): PeriodicTrigger {
        return PeriodicTrigger(periodSeconds, TimeUnit.SECONDS)
    }

    internal fun getBomScheduleJob(jobManager: JobManager): () -> Unit {
        return {
            val jobName = this.javaClass.name
            val alloc = jobManager.enableAlloc(jobName)
            if (alloc) {
                val remainJob: Boolean = run()
                if (!remainJob) {
                    jobTrigger.complete(jobName)
                    jobManager.free(jobName)
                }
            }
        }
    }

    fun allocJob() {
        jobTrigger.start(this.javaClass.name)
    }

    protected abstract fun run(): Boolean
}