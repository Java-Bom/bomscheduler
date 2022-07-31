package org.javabom.bomscheduler

class JobManager(val dbJobCoordinator: DBJobCoordinator) {

    val jobCollection= mutableListOf<Job>()

    fun getJob(name: String){
        jobCollection.get(name)
    }

}
