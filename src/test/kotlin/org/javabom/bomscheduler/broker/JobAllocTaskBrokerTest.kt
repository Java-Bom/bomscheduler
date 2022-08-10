package org.javabom.bomscheduler.broker

import org.assertj.core.api.Assertions.assertThat
import org.javabom.bomscheduler.processor.JobAllocTask
import org.junit.jupiter.api.Test
import org.springframework.util.StopWatch

internal class JobAllocTaskBrokerTest {

    @Test
    internal fun `작업을 가져올때 대기시간만큼 기다려야한다`() {
        //given
        val supplier = JobAllocTaskSupplier {
            listOf(JobAllocTask(jobName = "test", delayInMilliseconds = 1000))
        }
        val broker = JobAllocTaskBroker(listOf(supplier))

        //when
        val stopWatch = StopWatch()
        stopWatch.start()
        val task = broker.getJobAllocTask()
        stopWatch.stop()

        //then
        assertThat(task.jobName).isEqualTo("test")
        assertThat(stopWatch.totalTimeSeconds).isGreaterThan(1.0)
    }

    @Test
    internal fun `작업은 지연 시간 순으로 가져올 수 있다`() {
        //given
        val supplier = JobAllocTaskSupplier {
            listOf(
                JobAllocTask(jobName = "test", delayInMilliseconds = 1000),
                JobAllocTask(jobName = "test2", delayInMilliseconds = 400)
            )
        }
        val broker = JobAllocTaskBroker(listOf(supplier))

        //when
        val task = broker.getJobAllocTask()
        val task1 = broker.getJobAllocTask()
        val task2 = broker.getJobAllocTask()

        //then
        assertThat(task.jobName).isEqualTo("test2")
        assertThat(task1.jobName).isEqualTo("test2")
        assertThat(task2.jobName).isEqualTo("test")
    }
}