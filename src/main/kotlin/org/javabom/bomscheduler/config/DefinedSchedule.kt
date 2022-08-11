package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.schedule.BomSchedule
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class DefinedSchedule {

    @BomSchedule(jobName = "JOB_HELLO", cron = "5 * * * * *")
    fun printHello(){
        println("Hello")
        Thread.sleep(3000L)
    }

    @BomSchedule(jobName = "JOB_PRINT", cron = "5 * * * * *")
    fun printWorld(){
        println("World")
        Thread.sleep(5000L)
    }
}
