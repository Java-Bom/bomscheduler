package org.javabom.bomscheduler.test

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class TestRequest(
    var status: String = "WAIT",
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    fun complete() {
        this.status = "COMPLETE"
    }
}