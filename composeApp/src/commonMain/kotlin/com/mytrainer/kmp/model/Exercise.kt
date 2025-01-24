package com.mytrainer.kmp.model

import com.mytrainer.kmp.model.Series.DurationSeries
import com.mytrainer.kmp.model.Series.RepSeries

data class Exercise(
    val name: String,
    val series: List<Series>,
    val recoveryDurationS: Long
)

sealed class Series {
    data class RepSeries(val reps: Int) : Series()
    data class DurationSeries(val durationS: Long) : Series()
}

val DEGRESSIVE_REP_SERIES = listOf(
    RepSeries(12), RepSeries(10), RepSeries(8), RepSeries(8)
)
val CONSTANT_REP_SERIES_8 = listOf(
    RepSeries(8), RepSeries(8), RepSeries(8), RepSeries(8)
)
val CONSTANT_REP_SERIES_10 = listOf(
    RepSeries(10), RepSeries(10), RepSeries(10), RepSeries(10)
)
val CONSTANT_REP_SERIES_12 = listOf(
    RepSeries(12), RepSeries(12), RepSeries(12), RepSeries(12)
)
val CONSTANT_DURATION_SERIES_60 = listOf(
    DurationSeries(60), DurationSeries(60), DurationSeries(60)
)