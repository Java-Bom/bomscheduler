package org.javabom.bomscheduler.task

import org.javabom.bomscheduler.job.Job
import org.javabom.bomscheduler.job.JobMetadata
import org.springframework.stereotype.Component

class TaskRunner {
    private val executableTasks: MutableMap<String, Task> = mutableMapOf()

    fun putTasks(tasks: List<Task>) {
        tasks.forEach {
            executableTasks.putIfAbsent(it.jobName, it)
        }
    }

    fun findTaskByJobName(jobName: String) = executableTasks[jobName]

    fun consumeTask(jobName: String) = executableTasks.remove(jobName)
}