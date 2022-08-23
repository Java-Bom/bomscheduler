package org.javabom.bomscheduler.processor

import org.javabom.bomscheduler.broker.JobAllocTaskBroker
import org.javabom.bomscheduler.common.logger
import org.javabom.bomscheduler.coordinator.JobCoordinator
import org.javabom.bomscheduler.coordinator.JobManager
import org.springframework.context.SmartLifecycle
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


class JobAllocProcessor(
    private val allocId: String = UUID.randomUUID().toString(),
    private val jobCoordinator: JobCoordinator,
    private val jobAllocTaskBroker: JobAllocTaskBroker,
    private val jobManager: JobManager
) : SmartLifecycle {

    private val log = logger()
    private val lock: Lock = ReentrantLock()
    private val countDownLatch: CountDownLatch = CountDownLatch(1)
    private var running: Boolean = false
    private var pleaseStop: Boolean = true

    override fun start() {
        log.info { "start job alloc processor" }
        lock.withLock {
            check(!running) { "already running" }
            running = true
            pleaseStop = false
        }

        Thread {
            try {
                this.process()
            } catch (e: InterruptedException) {
                log.error("thread interrupted error.", e)
                Thread.currentThread().interrupt()
            } finally {
                running = false
                countDownLatch.countDown()
            }
        }.start()
    }

    private fun process() {
        while (!pleaseStop) {
            try {
                val jobAllocTask: JobAllocTask = jobAllocTaskBroker.getJobAllocTask()
                val request = jobAllocTask.toRequest(allocId)
                jobManager.alloc = jobCoordinator.alloc(request)

                if (jobManager.alloc) {
                    log.info { "alloc job-${request.allocId}" }
                }
            } catch (e: RuntimeException) {
                log.error("job alloc execute error.", e)
            }
        }
    }

    override fun stop() {
        lock.withLock {
            while (isRunning) {
                pleaseStop = true
                countDownLatch.await()
            }
        }
        log.info { "stop job alloc processor" }
    }

    override fun isRunning(): Boolean {
        return running
    }
}