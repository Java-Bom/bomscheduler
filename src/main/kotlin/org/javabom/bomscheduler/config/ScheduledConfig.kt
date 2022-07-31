package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.job.Job
import org.javabom.bomscheduler.job.JobCoordinator
import org.javabom.bomscheduler.job.JobNames
import org.javabom.bomscheduler.schedule.BomSchedule
import org.javabom.bomscheduler.schedule.BomScheduleMethodInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.aop.Advisor
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut
import java.util.*
import java.util.concurrent.Executors

@Configuration
class ScheduledConfig(
    val jobCoordinator: JobCoordinator
) {

    companion object {
        val hostName: String = UUID.randomUUID().toString()// systemConfig
    }

    @Bean
    fun jobNames(): JobNames {
        val annotations: List<BomSchedule> = ScheduledConfig::class.java
            .getAnnotationsByType(BomSchedule::class.java).toList()
        val jobNames = annotations.map { it.jobName }

        return JobNames(jobNames)
    }

    @Scheduled(cron = "10 * * * * *")
    fun allocSchedule() {
        jobNames().jobName.forEach { // component scan caching
            jobCoordinator.alloc(Job(it, hostName))
        }
    }
    // 1 = 2 start - master
    // 1 run 2 application start -> 1 exit -> 2x
    // db 권한 uuid -> config  (어 맞네?)

    @Bean
    fun interceptor(interceptor:BomScheduleMethodInterceptor) :Advisor{ // BomSchedule 어노테이션을 들고 있는 녀석들에게 interceptor 를 적용해준다.
        //인터셉터 설정
        val pointcut = AnnotationMatchingPointcut(BomSchedule::class.java)

        return DefaultPointcutAdvisor(pointcut, interceptor)
    }

    // 이녀석이 실행될때 인터셉터가 권한 확인하여 있으면 실행
    @Scheduled(cron = "* * * * * *")
    @BomSchedule(jobName = "JOB_A")
    fun jobA() {
        println("start job A")
    }

}
