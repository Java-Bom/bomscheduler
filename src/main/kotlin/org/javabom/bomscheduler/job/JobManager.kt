package org.javabom.bomscheduler.job

import org.javabom.bomscheduler.config.ScheduleConfig
import org.springframework.context.SmartLifecycle
import java.time.LocalDateTime

/**
 * JobCoordinator 와의 통신을 담당.
 * JobCoordinator 로 부터 주기적으로 다른 인스턴스 (HOST) 로 부터 빼앗아 올 수 있는 Job이 있는지 확인하여
 * JobCollection에 등록한다
 */
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

    /**
     * 주기적으로 JobCoordinator 에 빼앗아 올 수 있는 Job이 있는지 확인한다.
     * 빼앗아올 Job 이 있다면 Job의 정보를 변경하고 JobCoordinator 에 update 요청을 한다.
     * 그 후 JobColllection에 실행 가능한 Job 교체를 요청한다.
     */
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