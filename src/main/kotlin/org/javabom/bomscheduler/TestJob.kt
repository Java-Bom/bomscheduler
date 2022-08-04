package org.javabom.bomscheduler

import org.javabom.bomscheduler.coordinator.spec.SingleJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TestJob {


    @Scheduled(fixedRate = 1000)
    @SingleJob
    fun job(){
        print("test")
        Thread.sleep(10000L)
    }
}