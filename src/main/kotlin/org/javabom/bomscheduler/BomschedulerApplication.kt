package org.javabom.bomscheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BomschedulerApplication

fun main(args: Array<String>) {
    runApplication<BomschedulerApplication>(*args)
}
