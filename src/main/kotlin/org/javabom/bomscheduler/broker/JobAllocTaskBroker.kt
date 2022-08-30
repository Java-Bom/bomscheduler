package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.common.logger
import java.util.concurrent.DelayQueue

class JobAllocTaskBroker(private val allocTaskSuppliers: List<JobAllocTaskSupplier>) {

    private val log = logger()
    private val waitingJobs: MutableSet<JobAllocTask> = mutableSetOf()
    private val jobAllocTaskQueue: DelayQueue<JobAllocTask> = DelayQueue()

    fun getJobAllocTask(): JobAllocTask {
        generateTasks()
        val task = jobAllocTaskQueue.take()
        waitingJobs.remove(task)
        return task
    }

    private fun generateTasks() {
        val jobAllocTasks = allocTaskSuppliers
            .flatMap { it.createJobAllocTasks() }
            .filterNot { waitingJobs.contains(it) }

        log.info { jobAllocTasks }
        waitingJobs.addAll(jobAllocTasks)
        jobAllocTaskQueue.addAll(jobAllocTasks)
    }
}