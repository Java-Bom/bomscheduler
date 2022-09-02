package org.javabom.bomscheduler.task

import org.javabom.bomscheduler.config.ScheduleConfig
import org.javabom.bomscheduler.job.JobCollection
import org.javabom.bomscheduler.job.JobManager
import org.springframework.context.SmartLifecycle
import java.time.LocalDateTime

class TaskManager(
    private val jobManager: JobManager,
    private val jobCollection: JobCollection,
    private val taskRunner: TaskRunner
): SmartLifecycle {
    private var running = false
    private var keepGoing = false


    override fun start() {
        running = true
        keepGoing = true

        Thread {
            createExecutableTasks()
        }.start()
    }

    override fun stop() {
        keepGoing = false
    }

    override fun isRunning(): Boolean {
        return running
    }

    private fun createExecutableTasks() {
        val executableTasks = jobCollection.getDefinedJobs()
            .filter { it.instanceName == ScheduleConfig.HOST_NAME }
            .map {
                Task(
                    jobName = it.name,
                    callback = {
                        val executionTime = LocalDateTime.now()
                        val ttl = it.ttl

                        taskRunner.consumeTask(it.name)
                        jobManager.updateJobExecutionTime(
                            it.copy(
                                lastExecutionTime = executionTime,
                                instanceExpiredTime = executionTime.plusNanos(ttl)
                            )
                        )
                    }
                )
            }

        while (keepGoing) {
            taskRunner.putTasks(executableTasks)
        }

        running = false
    }
}