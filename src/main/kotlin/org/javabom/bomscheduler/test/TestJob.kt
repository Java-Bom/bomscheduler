package org.javabom.bomscheduler.test

import org.javabom.bomscheduler.common.logger
import org.javabom.bomscheduler.processor.SingleJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TestJob(
    private val testService: TestService
) {

    private val log = logger()

    @Scheduled(fixedRate = 1000)
    @SingleJob
    fun job() {
        val id = testService.request()

        //wait
        val datetime = LocalDateTime.now().plusSeconds(10)
        log.info { "start" }
        while (datetime.isAfter(LocalDateTime.now())) {
        }
        log.info { "end" }

        testService.complete(id)
    }
}