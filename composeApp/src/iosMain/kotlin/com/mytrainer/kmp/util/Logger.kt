package com.mytrainer.kmp.util

actual val Log: Logger = iOSLogger()

class iOSLogger : Logger {
    override fun e(text: String) {
        // TODO
    }
}