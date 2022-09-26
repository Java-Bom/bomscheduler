package org.javabom.bomscheduler.task

import org.javabom.bomscheduler.job.Job
import org.javabom.bomscheduler.job.JobMetadata
import org.springframework.stereotype.Component

/**
 * TaskRunner 현재 실행 가능한 Task 를 가지고 있는 클래스
 * Task가 소비될 시 해당 Task를 제거한다
 */
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