package org.javabom.bomscheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class BomschedulerApplication

fun main(args: Array<String>) {
    runApplication<BomschedulerApplication>(*args)
}
