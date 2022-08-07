package org.javabom.bomscheduler

import org.javabom.bomscheduler.processor.SingleJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TestJob {

    @Scheduled(fixedRate = 1000)
    @SingleJob
    fun job(){
        println("test")
    }
}