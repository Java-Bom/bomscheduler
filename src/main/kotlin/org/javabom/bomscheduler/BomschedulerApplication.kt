package org.javabom.bomscheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BomschedulerApplication

fun main(args: Array<String>) {
    runApplication<BomschedulerApplication>(*args)
}
