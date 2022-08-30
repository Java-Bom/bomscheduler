package org.javabom.bomscheduler.coordinator

import org.assertj.core.api.Assertions.assertThat
import org.javabom.bomscheduler.support.BomschedulerTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@BomschedulerTest
internal class SingleJpaJobCoordinatorTest(
    private val jobAllocJpaRepository: JobAllocJpaRepository,
    private val jobAllocHistoryJpaRepository: JobAllocHistoryJpaRepository,
    private val jpaJobCoordinator: JpaJobCoordinator,
) {


    @AfterEach
    internal fun tearDown() {
        jobAllocJpaRepository.deleteAll()
        jobAllocHistoryJpaRepository.deleteAll()
    }

    @Test
    internal fun `작업이 없는 경우 새로운 작업이 생성된다`() {
        //given
        val request = JobAllocRequest(allocId = "test", jobName = "testJob")
        //when
        jpaJobCoordinator.alloc(request)

        //then
        val allocList = jobAllocJpaRepository.findAll()
        assertThat(allocList.size).isEqualTo(1)
        val alloc = allocList[0]
        assertThat(alloc.allocId).isEqualTo(request.allocId)
        assertThat(alloc.startDateTime).isEqualTo(request.startDateTime)
        assertThat(alloc.endDateTime).isEqualTo(request.endDateTime)
    }

    @Test
    internal fun `같은 할당자가 작업 할당할 시 종료시간이 변경된다`() {
        //given
        val request1 = JobAllocRequest(
            allocId = "test",
            jobName = "testJob"
        )
        jpaJobCoordinator.alloc(request1)
        val request2 = JobAllocRequest(
            allocId = request1.allocId,
            jobName = request1.jobName,
            startDateTime = request1.endDateTime.plusSeconds(10),
            endDateTime = request1.endDateTime.plusSeconds(30)
        )

        //when
        jpaJobCoordinator.alloc(request2)

        //then
        val allocList = jobAllocJpaRepository.findAll()
        assertThat(allocList.size).isEqualTo(1)
        val alloc = allocList[0]
        assertThat(alloc.endDateTime).isEqualTo(request2.endDateTime)
    }

    @Test
    internal fun `다른 할당자가 작업 할당을 하려면 시작시간이 현재 할당자의 종료시간 이후일떄 변경된다`() {
        //given
        val request1 = JobAllocRequest(
            allocId = "test",
            jobName = "testJob"
        )
        jpaJobCoordinator.alloc(request1)
        val request2 = JobAllocRequest(
            allocId = "test2",
            jobName = "testJob",
            startDateTime = request1.endDateTime.plusSeconds(10),
            endDateTime = request1.endDateTime.plusSeconds(30)
        )

        //when
        jpaJobCoordinator.alloc(request2)

        //then
        val allocList = jobAllocJpaRepository.findAll()
        assertThat(allocList.size).isEqualTo(1)
        val alloc = allocList[0]
        assertThat(alloc.allocId).isEqualTo(request2.allocId)
    }


    @Test
    internal fun `다른 할당자가 작업 할당을 하려면 시작시간이 현재 할당자의 종료시간 이전이면 변경되지 않는다`() {
        //given
        val request1 = JobAllocRequest(
            allocId = "test",
            jobName = "testJob"
        )
        jpaJobCoordinator.alloc(request1)
        val request2 = JobAllocRequest(
            allocId = "test2",
            jobName = "testJob",
            startDateTime = request1.endDateTime,
            endDateTime = request1.endDateTime.plusSeconds(30)
        )

        //when
        jpaJobCoordinator.alloc(request2)

        //then
        val allocList = jobAllocJpaRepository.findAll()
        assertThat(allocList.size).isEqualTo(1)
        val alloc = allocList[0]
        assertThat(alloc.allocId).isEqualTo(request1.allocId)
    }

}