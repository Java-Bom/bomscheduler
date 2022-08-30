package org.javabom.bomscheduler.broker

import org.javabom.bomscheduler.processor.JobAllocTask

interface TriggerJobAllocTaskCreator {
    fun jobAllocTask(): JobAllocTask
}