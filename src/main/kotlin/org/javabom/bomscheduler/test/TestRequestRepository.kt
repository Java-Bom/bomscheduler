package org.javabom.bomscheduler.test

import org.springframework.data.jpa.repository.JpaRepository

interface TestRequestRepository : JpaRepository<TestRequest, Long>