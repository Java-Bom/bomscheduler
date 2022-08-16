package org.javabom.bomscheduler.test

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TestService(
    private val testRequestRepository: TestRequestRepository
) {

    fun request(): Long {
        return testRequestRepository.save(TestRequest()).id!!
    }

    fun complete(id: Long) {
        val testRequest = testRequestRepository.findById(id).orElseThrow { IllegalStateException() }
        testRequest.complete()
    }
}