package org.javabom.bomscheduler.schedule

import org.springframework.core.annotation.AliasFor
import org.springframework.scheduling.annotation.Scheduled

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Scheduled
annotation class BomSchedule(

    val jobName: String,

    @get:AliasFor(annotation = Scheduled::class, attribute = "cron")
    val cron: String = ""

)
