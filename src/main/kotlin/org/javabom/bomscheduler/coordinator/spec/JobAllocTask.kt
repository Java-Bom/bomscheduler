package org.javabom.bomscheduler.coordinator.spec

import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

data class JobAllocTask(
    val jobName: String,
    val delayInMilliseconds: Long
) : Delayed {

    private var startTime: Long = 0

    init {
        this.startTime = System.currentTimeMillis() + delayInMilliseconds
    }

    override fun getDelay(unit: TimeUnit): Long {
        val diff = startTime - System.currentTimeMillis()
        return unit.convert(diff, TimeUnit.MILLISECONDS)
    }

    override fun compareTo(other: Delayed): Int {
        return (startTime - (other as JobAllocTask).startTime).toInt()
    }

    fun toRequest(allocId: String): JobAllocRequest {
        return JobAllocRequest(
            allocId = allocId, jobName = jobName
        )
    }
}