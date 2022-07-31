package org.javabom.bomscheduler.job

import org.javabom.bomscheduler.config.ScheduledConfig
import org.springframework.stereotype.Component

@Component
class DBJobCoordinator(val jobManager: JobManager) : JobCoordinator{

    private val jobMemoryDB = mutableMapOf<String ,Job>() // memory DB

    override fun alloc(job: Job) {
        // 할당해도 되는지를 검사해줄 필요가 있다
        // 1. 다른녀석이 할당했지만 할당하고 오랜시간이 지났다면 베타적으로 권한 습득 : TTL

        synchronized(jobManager) {
            if (jobMemoryDB[job.name] == null){
                jobMemoryDB[job.name] = job
                jobManager.setExecutableJob(job)
            }else{
                if (jobMemoryDB[job.name]?.instanceNumber == ScheduledConfig.hostName) {
                    jobManager.setExecutableJob(job)
                }else{
                    jobManager.removeExectableJob(job)
                }
            }
        }
    }

}
