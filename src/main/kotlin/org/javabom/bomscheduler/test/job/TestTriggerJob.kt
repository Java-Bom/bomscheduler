package org.javabom.bomscheduler.test.job

import org.javabom.bomscheduler.spec.AbstractTriggerJob
import org.springframework.stereotype.Component

@Component
class TestTriggerJob : AbstractTriggerJob(periodSeconds = 1) {

    override fun run(): Boolean {
        val number: Int = (1..5).random()
        println("test range $number")
        if (number == 4) {
            return false
        }
        return true
    }
}