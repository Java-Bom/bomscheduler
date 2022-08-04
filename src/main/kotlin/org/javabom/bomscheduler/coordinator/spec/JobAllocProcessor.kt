package org.javabom.bomscheduler.coordinator.spec

import org.springframework.context.SmartLifecycle
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.DelayQueue
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


class JobAllocProcessor(private val jobCoordinator: JobCoordinator) : SmartLifecycle {

    private val lock: Lock = ReentrantLock()
    private val countDownLatch: CountDownLatch = CountDownLatch(1)
    private val allocId: String = UUID.randomUUID().toString()
    private val jobAllocTaskQueue: DelayQueue<JobAllocTask> = DelayQueue()
    private var running: Boolean = false
    private var pleaseStop: Boolean = true

    override fun start() {
        lock.withLock {
            check(!running) { "Already running" }
            running = true
            pleaseStop = false
        }

        Thread {
            try {
                this.process()
            } catch (e: InterruptedException) {

            } finally {
                running = false
                countDownLatch.countDown()
            }
        }.start()
    }

    private fun process() {
        jobAllocTaskQueue.put(jobCoordinator.createDelayJobAlloc())
        while (!pleaseStop) {
            try {
                val jobAllocTask: JobAllocTask = jobAllocTaskQueue.take()
                jobCoordinator.alloc(jobAllocTask.toRequest(allocId))
                jobAllocTaskQueue.put(jobCoordinator.createDelayJobAlloc())
            } catch (e: RuntimeException) {

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
    }

    override fun isRunning(): Boolean {
        return running
    }
}