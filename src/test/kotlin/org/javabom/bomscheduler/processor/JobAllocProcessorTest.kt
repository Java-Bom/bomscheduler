package org.javabom.bomscheduler.processor


import org.assertj.core.api.Assertions.assertThat
import org.javabom.bomscheduler.broker.JobAllocTaskBroker
import org.javabom.bomscheduler.broker.JobAllocTaskSupplier
import org.javabom.bomscheduler.common.logger
import org.javabom.bomscheduler.coordinator.JobCoordinator
import org.junit.jupiter.api.Test
import org.springframework.util.StopWatch
import java.util.concurrent.CountDownLatch

internal class JobAllocProcessorTest {
    private val log = logger()

    @Test
    internal fun `작업 딜레이 시간만큼 지나야 작업이 실행된다`() {
        //3개의 작업 수행 설정
        val latch = CountDownLatch(3)
        val coordinator = TestJobCoordinator()
        val stopWatch = StopWatch()
        val processor = JobAllocProcessor(
            jobCoordinator = coordinator,
            jobAllocTaskBroker = jobAllocTaskBroker(latch)
        )

        //when - 3개의 작업 1초 지연 설정
        stopWatch.start()
        processor.start()
        latch.await()
        processor.stop()
        stopWatch.stop()
        waitStop(processor)

        //then 3초 후 세개 이상의 작업 수행
        assertThat(coordinator.count).isEqualTo(3)
        assertThat(stopWatch.totalTimeSeconds).isGreaterThan(3.0)
    }

    @Test
    internal fun `실행중인 작업이 끝내야 processor가 종료된다`() {
        //1개의 작업/ 1초 지연 설정
        val latch = CountDownLatch(1)
        val coordinator = TestJobCoordinator()
        val processor = JobAllocProcessor(
            jobCoordinator = coordinator,
            jobAllocTaskBroker = jobAllocTaskBroker(latch)
        )

        //when - 1초 지연 설정
        processor.start()
        latch.await()
        processor.stop()
        waitStop(processor)

        //then
        assertThat(coordinator.count).isEqualTo(1)
    }

    private fun waitStop(processor: JobAllocProcessor) {
        while (processor.isRunning) {
            log.info { "작업종료 대기중" }
        }
    }

    private fun jobAllocTaskBroker(latch: CountDownLatch): JobAllocTaskBroker {
        return JobAllocTaskBroker(listOf(JobAllocTaskSupplier {
            latch.countDown()
            listOf(JobAllocTask(jobName = "test", delayInMilliseconds = 1000))
        }))
    }
}

class TestJobCoordinator : JobCoordinator {
    var count: Int = 0
    override fun alloc(request: JobAllocRequest) {
        this.count++
    }
}




