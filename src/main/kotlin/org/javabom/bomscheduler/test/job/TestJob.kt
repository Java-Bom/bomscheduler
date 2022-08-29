package org.javabom.bomscheduler.test.job

import org.javabom.bomscheduler.common.logger
import org.javabom.bomscheduler.spec.BomScheduleJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TestJob {

    private val log = logger()

    @Scheduled(fixedRate = 1000)
    @BomScheduleJob
    fun job() {
        log.info { "execute job" }
    }
}