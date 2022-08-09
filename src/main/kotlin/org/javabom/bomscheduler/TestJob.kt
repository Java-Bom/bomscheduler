package org.javabom.bomscheduler

import org.javabom.bomscheduler.processor.SingleJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TestJob {

    @Scheduled(fixedRate = 1000)
    @SingleJob
    fun job() {
        val datetime = LocalDateTime.now().plusSeconds(10)
        println("start")
        while (datetime.isAfter(LocalDateTime.now())) {
        }
        println("end")
    }
}