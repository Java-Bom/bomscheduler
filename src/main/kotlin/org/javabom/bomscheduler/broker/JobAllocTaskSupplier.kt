package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask

interface JobAllocTaskSupplier {

    fun createJobAllocTask(): JobAllocTask
}