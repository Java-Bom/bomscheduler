package org.javabom.bomscheduler.job

import org.javabom.bomscheduler.config.ScheduleConfig
import org.springframework.context.SmartLifecycle
import java.time.LocalDateTime

class JobManager(
    private val jobCoordinator: JobCoordinator,
    private val jobCollection: JobCollection
): SmartLifecycle {

    private var running = false
    private var keepGoing = false

    override fun start() {
        running = true
        keepGoing = true

        Thread {
            updateJob()
        }.start()
    }

    override fun stop() {
        this.keepGoing = false
    }

    override fun isRunning(): Boolean {
        return running
    }

    private fun updateJob() {
        while (keepGoing) {
            val (currentJobs, anotherJobs) = jobCoordinator.getDefinedJob()
                .partition { it.instanceName == ScheduleConfig.HOST_NAME }

            val now = LocalDateTime.now()

            val exchangedJobs = anotherJobs.filter { it.isExpired() }
                .map {
                    val ttl = it.ttl

                    it.copy(
                        instanceName = ScheduleConfig.HOST_NAME,
                        instanceExpiredTime = now.plusNanos(ttl)
                    )
                }


            if (exchangedJobs.isNotEmpty()) {
                jobCoordinator.updateJobs(exchangedJobs)
            }

            jobCollection.replaceJobs(currentJobs + exchangedJobs)
        }

        running = false
    }

    fun updateJobExecutionTime(job: Job) {
        jobCoordinator.updateJobs(listOf(job))
    }
}