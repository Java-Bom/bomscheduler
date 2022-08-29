package org.javabom.bomscheduler.test.service

import org.springframework.data.jpa.repository.JpaRepository

interface TestRequestRepository : JpaRepository<TestRequest, Long>