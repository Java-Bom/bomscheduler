package org.javabom.bomscheduler.config

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.javabom.bomscheduler.coordinator.JobManager
import org.javabom.bomscheduler.spec.BomScheduleJob

class BomScheduleJobInterceptor(
    private val jobManager: JobManager,
) : MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any? {
        if (jobManager.enableAlloc(BomScheduleJob.DEFAULT_JOB)) {
            return invocation.proceed()
        }
        return null
    }
}
