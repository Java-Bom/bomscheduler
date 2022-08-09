package org.javabom.bomscheduler.broker

import org.assertj.core.api.Assertions.assertThat
import org.javabom.bomscheduler.processor.JobAllocTask
import org.junit.jupiter.api.Test
import org.springframework.util.StopWatch

internal class JobAllocTaskBrokerTest {
    private val supplier: JobAllocTaskSupplier = object : JobAllocTaskSupplier {
        override fun createJobAllocTask(): JobAllocTask {
            return JobAllocTask(jobName = "test", delayInMilliseconds = 1000L)
        }
    }

    private val broker: JobAllocTaskBroker = JobAllocTaskBroker(listOf(supplier))

    @Test
    internal fun `작업을 가져올때 대기시간만큼 기다려야한다`() {
        val stopWatch = StopWatch()
        stopWatch.start()
        val task = broker.getJobAllocTask()
        stopWatch.stop()

        assertThat(task.jobName).isEqualTo("test")
        assertThat(stopWatch.totalTimeSeconds).isGreaterThan(1.0)
    }
}