package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.job.Job
import org.javabom.bomscheduler.schedule.BomSchedule
import org.springframework.aop.PointcutAdvisor
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Method

@Configuration
class ScheduleInterceptorConfig {

    fun interceptorConfig(){
        val scanComponent = ScheduledConfig::class.java
        val methods: List<Method> = scanComponent.methods
            .filter { it.getAnnotationsByType(BomSchedule::class.java) != null }
        PointcutAdvisor
    }
}
