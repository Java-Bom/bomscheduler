package org.javabom.bomscheduler.schedule

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.javabom.bomscheduler.task.TaskManager
import org.javabom.bomscheduler.task.TaskRunner

/**
 * @BomSchedule 이 등록되어있는 Schedule 들이 실행 되기전 TaskRunner에 소비 가능한 Task가 있는지 (jobName 기준) 확인 후
 * 있을 경우에만 task 를 소비하고 정의된 행동을 수행한다.
 */
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