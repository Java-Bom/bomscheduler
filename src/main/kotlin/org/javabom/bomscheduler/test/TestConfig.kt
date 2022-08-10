package org.javabom.bomscheduler.test

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(
    value = [
        "org.javabom.bomscheduler.test"
    ]
)
@EnableJpaRepositories(
    value = [
        "org.javabom.bomscheduler.test"
    ]
)
@Configuration
class TestConfig