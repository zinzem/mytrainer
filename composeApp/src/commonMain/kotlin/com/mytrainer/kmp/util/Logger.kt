package com.mytrainer.kmp.util

expect val Log: Logger

interface Logger {
    fun e(text: String)
}