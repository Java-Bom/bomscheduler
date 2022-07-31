package org.javabom.bomscheduler

class ScheduledConfig {

    @BomSchedule(jobName = "jobA", cron= "* * * * *")
    fun jobA(){
        println(" start job A")
    }

}
