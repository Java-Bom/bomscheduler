package org.javabom.bomscheduler.coordinator

interface JobTrigger {
    fun start(jobName: String)
    fun complete(jobName: String)
}