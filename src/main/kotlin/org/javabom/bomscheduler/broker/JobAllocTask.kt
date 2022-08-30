package org.javabom.bomscheduler.broker

import java.time.LocalDateTime
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

data class JobAllocTask(
    val jobName: String,
    val delayInMilliseconds: Int = DEFAULT_DELAY_MILLI_SEC,
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

    fun getStartDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }

    fun getEndDateTime(): LocalDateTime {
        //모니터링 반복 주기 + 20초
        //ex ) 10초마다 돌시 30초의 만료시간
        return LocalDateTime.now().plusSeconds((delayInMilliseconds % 1_000 + 20).toLong())
    }

    companion object {
        const val DEFAULT_DELAY_MILLI_SEC = 10_000
    }
}