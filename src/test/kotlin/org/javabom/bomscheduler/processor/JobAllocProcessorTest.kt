package org.javabom.bomscheduler.processor


import org.assertj.core.api.Assertions.assertThat
import org.javabom.bomscheduler.broker.JobAllocTaskBroker
import org.javabom.bomscheduler.broker.JobAllocTaskSupplier
import org.javabom.bomscheduler.coordinator.JobCoordinator
import org.javabom.bomscheduler.coordinator.JobManager
import org.junit.jupiter.api.Test
import org.springframework.util.StopWatch
import java.util.concurrent.CountDownLatch

internal class JobAllocProcessorTest {

    @Test
    internal fun `작업 딜레이 시간만큼 지나야 작업이 실행된다`() {
        //3개의 작업 수행 설정
        val jobAllocLatch = CountDownLatch(3)
        val coordinator = TestJobCoordinator(jobAllocLatch)

        val jobSupplierLatch = CountDownLatch(3)
        val stopWatch = StopWatch()
        val processor = JobAllocProcessor(
            jobCoordinator = coordinator,
            jobAllocTaskBroker = jobAllocTaskBroker(jobSupplierLatch),
            jobManager = JobManager()
        )

        //when - 3개의 작업 1초 지연 설정
        stopWatch.start()
        processor.start()
        jobSupplierLatch.await()
        processor.stop()
        stopWatch.stop()
        jobAllocLatch.await()

        //then 3초 후 세개 이상의 작업 수행
        assertThat(coordinator.count).isEqualTo(3)
        assertThat(stopWatch.totalTimeSeconds).isGreaterThan(3.0)
    }

    @Test
    internal fun `실행중인 작업이 끝내야 processor가 종료된다`() {
        //1개의 작업/ 1초 지연 설정
        val jobAllocLatch = CountDownLatch(1)
        val coordinator = TestJobCoordinator(jobAllocLatch)
        val jobSupplierLatch = CountDownLatch(1)
        val processor = JobAllocProcessor(
            jobCoordinator = coordinator,
            jobAllocTaskBroker = jobAllocTaskBroker(jobSupplierLatch),
            jobManager = JobManager()
        )

        //when - 1초 지연 설정
        processor.start()
        jobSupplierLatch.await()
        processor.stop()
        jobAllocLatch.countDown()

        //then
        assertThat(coordinator.count).isEqualTo(1)
    }

    private fun jobAllocTaskBroker(latch: CountDownLatch): JobAllocTaskBroker {
        return JobAllocTaskBroker(listOf(JobAllocTaskSupplier {
            latch.countDown()
            listOf(JobAllocTask(jobName = "test", delayInMilliseconds = 1000))
        }))
    }
}

class TestJobCoordinator(private val latch: CountDownLatch) : JobCoordinator {
    var count: Int = 0
    override fun alloc(request: JobAllocRequest): Boolean {
        this.count++
        latch.countDown()
        return true
    }
}




