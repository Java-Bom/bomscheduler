package org.javabom.bomscheduler.config

import org.javabom.bomscheduler.job.Job
import org.javabom.bomscheduler.job.JobCollection
import org.javabom.bomscheduler.job.JobCoordinator
import org.javabom.bomscheduler.schedule.BomSchedule
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@EnableConfigurationProperties(BomProperty::class)
class ScheduleConfig(val bomProperty: BomProperty, val jobCoordinator: JobCoordinator) {

    companion object {
        val HOST_NAME: String = UUID.randomUUID().toString() // systemConfig
    }

    @Bean
    fun jobCollection(): JobCollection {
        val annotations: List<BomSchedule> = DefinedSchedule::class.java
            .methods
            .mapNotNull { method -> method.getAnnotation(BomSchedule::class.java) }

        val preDefinedJobs = jobCoordinator.getDefinedJob()
        val nowDefinedJobs = annotations
            .map { Job(name = it.jobName, instanceName = HOST_NAME, ttl = bomProperty.ttl) }
        val executeJobs = registerJobs(preDefinedJobs, nowDefinedJobs)

        return JobCollection(executeJobs)
    }

    private fun registerJobs(preDefinedJobs: List<Job>, nowDefinedJobs: List<Job>): List<Job> {
        preDefinedJobs.removeJobs(nowDefinedJobs)
        val updateJobs = preDefinedJobs.updateJobs(nowDefinedJobs)
        val newJobs = preDefinedJobs.newJobs(nowDefinedJobs)
        return updateJobs + newJobs
    }

    private fun List<Job>.updateJobs(nowDefinedJobs: List<Job>): List<Job> {
        val updateJobs = this.filter { prev -> nowDefinedJobs.map { it.name }.contains(prev.name) }
            .map { Job(name = it.name, instanceName = HOST_NAME, ttl = bomProperty.ttl, version = (it.version + 1)) }
        jobCoordinator.updateJobs(updateJobs)
        return updateJobs
    }

    private fun List<Job>.removeJobs(nowDefinedJobs: List<Job>) {
        val removeJobs = this.filter { prev -> !nowDefinedJobs.map { it.name }.contains(prev.name) }
        jobCoordinator.removeJobs(removeJobs)
    }

    private fun List<Job>.newJobs(nowDefinedJobs: List<Job>): List<Job> {
        val newJobs = nowDefinedJobs.filter { now -> !this.map { it.name }.contains(now.name) }
        jobCoordinator.saveJobs(newJobs)
        return newJobs
    }


}
