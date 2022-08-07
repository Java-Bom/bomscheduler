package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask
import java.util.concurrent.DelayQueue

class JobAllocTaskBroker(private val allocTaskSuppliers: List<JobAllocTaskSupplier>) {

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
            .map { it.createJobAllocTask() }
            .filterNot { waitingJobs.contains(it) }

        waitingJobs.addAll(jobAllocTasks)
        jobAllocTaskQueue.addAll(jobAllocTasks)
    }
}