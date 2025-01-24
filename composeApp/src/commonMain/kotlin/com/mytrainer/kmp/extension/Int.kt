package com.mytrainer.kmp.extension

fun Int.zeroPrefixed(length: Int = 2): String {
    val stringValue = this.toString()
    val prefix = (stringValue.length until length).joinToString { "0" }
    return "$prefix$stringValue"
}