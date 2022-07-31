package org.javabom.bomscheduler

class DBJobCoordinator {

    private val jobList = mutableListOf<Job>()

    fun alloc(job: Job) {
        jobList.add(job)
    }

    fun dealloc(job: Job){
        jobList.remove(job)
    }

    fun getJob(name: String): Job? {
        return jobList
            .find{it.name == name}
    }


}

// 1

/// job list (table)
/// jobName : instanceNum
/// A       : 1
/// B       : 1
/// C       : 1

/// job execute...
