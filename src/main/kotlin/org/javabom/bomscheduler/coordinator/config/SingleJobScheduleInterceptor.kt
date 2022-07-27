package org.javabom.bomscheduler.coordinator.config

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.javabom.bomscheduler.coordinator.spec.JobManager

class SingleJobScheduleInterceptor(
    private val jobManager: JobManager
) : MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any {
        return invocation
    }
}
