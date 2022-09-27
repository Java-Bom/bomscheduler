package org.javabom.bomscheduler.task

data class Task (
    val jobName: String,
    val callback: () -> Unit
) {

    fun consuming() {
        callback.invoke()
    }
}
