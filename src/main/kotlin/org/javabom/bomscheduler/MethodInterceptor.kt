package org.javabom.bomscheduler

import org.springframework.cglib.proxy.MethodInterceptor
import org.springframework.cglib.proxy.MethodProxy
import java.lang.reflect.Method

class MethodInterceptor(val scheduled: Scheduled) {

    fun exeucte(){
        val scanComponent = ScheduledConfig::class.java
        scanComponent.methods
            .filter { it.getAnnotationsByType(BomSchedule::class.java) != null }
            .forEach{ Job(it.name, null, it.invoke()) }

    }

}
