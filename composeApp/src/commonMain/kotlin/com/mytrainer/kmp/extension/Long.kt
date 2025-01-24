package com.mytrainer.kmp.extension

fun Long.formatDuration(): String {
    val hours = this / 3600
    val mins = this % 3600 / 60

    return if (hours > 0) {
        "${hours}h ${mins}min"
    } else {
        "${mins}min"
    }
}