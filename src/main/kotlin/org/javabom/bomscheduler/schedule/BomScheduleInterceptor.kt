package org.javabom.bomscheduler.schedule

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.javabom.bomscheduler.task.TaskManager
import org.javabom.bomscheduler.task.TaskRunner

class BomScheduleInterceptor(
    private val taskRunner: TaskRunner
) : MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? {
        val jobName = invocation.method.getAnnotation(BomSchedule::class.java).jobName

        val task = taskRunner.findTaskByJobName(jobName)

        if (task != null) {
            task.consuming()
            return invocation.proceed()
        }

        return null
    }
}