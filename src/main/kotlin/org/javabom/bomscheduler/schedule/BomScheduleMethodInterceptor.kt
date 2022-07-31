package org.javabom.bomscheduler.schedule

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.javabom.bomscheduler.job.JobManager
import org.springframework.stereotype.Component

@Component
class BomScheduleMethodInterceptor(
    private val jobManager: JobManager,
) : MethodInterceptor {

    override fun invoke(invocation: MethodInvocation): Any? {
        val bomSchedule = invocation.method.getAnnotationsByType(BomSchedule::class.java).first()

        val jobName: String = bomSchedule.jobName

        val job = jobManager.getJob(jobName)
        if (job != null) { // 권한을 가지고 있다면 null이 아니다
            return invocation.proceed()
        }
        return null
    }

}
