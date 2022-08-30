package org.javabom.bomscheduler.broker

interface TriggerJobAllocTaskCreator {
    fun jobAllocTask(): JobAllocTask
}