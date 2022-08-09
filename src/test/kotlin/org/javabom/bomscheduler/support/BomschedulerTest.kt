package org.javabom.bomscheduler.support

import org.javabom.bomscheduler.config.CoordinatorConfig
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@EnableAutoConfiguration
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [CoordinatorConfig::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
annotation class BomschedulerTest
