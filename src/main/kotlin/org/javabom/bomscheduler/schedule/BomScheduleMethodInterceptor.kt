package org.javabom.bomscheduler.schedule

import org.javabom.bomscheduler.job.JobManager
import org.springframework.cglib.proxy.MethodInterceptor
import org.springframework.cglib.proxy.MethodProxy
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
class BomScheduleMethodInterceptor(
    private val jobManager: JobManager,
) : MethodInterceptor {

    fun exeucte() {

    }

    /// @BomSchedule(jobName = "jobA")
    /// fun method1(){}
    ///
    override fun intercept(proxy: Any?, method: Method, args: Array<out Any>?, p3: MethodProxy?): Any {
        val bomSchedule = method.getAnnotationsByType(BomSchedule::class.java).first()

        val jobName: String = bomSchedule.jobName

        val job = jobManager.getJob(jobName)
        if (job != null) {
            return method.invoke(proxy, args)
        }


    }

}
