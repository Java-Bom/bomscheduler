package org.javabom.bomscheduler.job

import org.springframework.stereotype.Component

@Component
class JobManager {

    // 권한을 가지고 소비해도 되는 작업 목록
    private val executableJobs= mutableMapOf<String,Job>()

    fun getJob(name: String):Job?{
        return executableJobs[name]
    }

    fun setExecutableJob(job: Job){
        executableJobs[job.name]= job
    }

    fun removeExectableJob(job: Job){
        executableJobs.remove(job.name)
    }

}
