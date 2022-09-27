package org.javabom.bomscheduler.task

import org.javabom.bomscheduler.config.ScheduleConfig
import org.javabom.bomscheduler.job.JobCollection
import org.javabom.bomscheduler.job.JobManager
import org.springframework.context.SmartLifecycle
import java.time.LocalDateTime

/**
 * 주기적으로 JobCollection 에서 실행가능한 Job을 기반으로 Task를 생성해 TaskRunner 에 전달하는 클래스
 * JobManager를 의존하고있는 이유는 Task가 소비될 때 callback으로 Jobmanager에게 해당 job의 실행 시간 update 요청을 하기 위함
 */
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

    /**
     * Task를 생성하는 메소드
     * 생성 후 TaskRunner에 전달한다
     */
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