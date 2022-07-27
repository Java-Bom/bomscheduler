package org.javabom.bomscheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TestJob {


    @Scheduled(fixedDelay = 1000)
    @Scheduled
    fun job(){
        print("test")
    }
}