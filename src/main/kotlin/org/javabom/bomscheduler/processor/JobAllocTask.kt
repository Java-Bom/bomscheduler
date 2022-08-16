package org.javabom.bomscheduler.processor

import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

data class JobAllocTask(
    val jobName: String,
    val delayInMilliseconds: Int
) : Delayed {

    private var startMilliseconds: Long = 0

    init {
        this.startMilliseconds = System.currentTimeMillis() + delayInMilliseconds
    }

    override fun getDelay(unit: TimeUnit): Long {
        val diff = startMilliseconds - System.currentTimeMillis()
        return unit.convert(diff, TimeUnit.MILLISECONDS)
    }

    override fun compareTo(other: Delayed): Int {
        return (startMilliseconds - (other as JobAllocTask).startMilliseconds).toInt()
    }

    fun toRequest(allocId: String): JobAllocRequest {
        return JobAllocRequest(
            allocId = allocId, jobName = jobName
        )
    }
}