package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.schedule.BomSchedule

class ScheduledConfig {

    @BomSchedule(jobName = "jobA", cron= "* * * * *")
    fun jobA(){
        println(" start job A")
    }

}
