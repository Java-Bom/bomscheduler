package org.javabom.bomscheduler.job

import org.springframework.stereotype.Component

@Component
class JobManager(val dbJobCoordinator: DBJobCoordinator) {

    val jobCollection= mutableMapOf<String,Job>()

    fun getJob(name: String):Job?{
        return jobCollection[name]
    }

}
