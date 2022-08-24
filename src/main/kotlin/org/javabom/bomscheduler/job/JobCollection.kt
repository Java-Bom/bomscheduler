package org.javabom.bomscheduler.job

class JobCollection(definedJobs : List<Job> = emptyList()) {

    init {
        definedJobs.forEach{ println("Register Job ${it.name}")}
    }
}
