package org.javabom.bomscheduler.test.controller

import org.javabom.bomscheduler.test.job.TestTriggerJob
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController(
    private val triggerJob: TestTriggerJob,
) {

    @PostMapping("/test")
    fun executeJob() {
        triggerJob.allocJob()
    }
}