package org.javabom.bomscheduler.broker

annotation class BomScheduleJob(val jobName: String = "DEFAULT_SCHEDULER") {
    companion object {
        val DEFAULT_JOB = BomScheduleJob()
    }
}
