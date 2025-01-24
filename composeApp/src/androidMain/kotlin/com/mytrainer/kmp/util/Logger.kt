package com.mytrainer.kmp.util

import com.mytrainer.android.BuildConfig

actual val Log: Logger = AndroidLogger()

class AndroidLogger : Logger {
    override fun e(text: String) {
        android.util.Log.e(BuildConfig.APPLICATION_ID, text)
    }
}