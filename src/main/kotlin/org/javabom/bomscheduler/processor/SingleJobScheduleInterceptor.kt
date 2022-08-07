package org.javabom.bomscheduler.processor

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.javabom.bomscheduler.coordinator.JobManager

class SingleJobScheduleInterceptor(
    private val jobManager: JobManager
) : MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any? {
        if(jobManager.lock){
            return null
        }
        return invocation.proceed()
    }
}
