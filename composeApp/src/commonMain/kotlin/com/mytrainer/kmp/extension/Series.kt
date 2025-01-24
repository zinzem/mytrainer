package com.mytrainer.kmp.extension

import com.mytrainer.kmp.model.Series

fun Series.count() = when (this) {
    is Series.RepSeries -> reps.toLong()
    is Series.DurationSeries -> durationS
}