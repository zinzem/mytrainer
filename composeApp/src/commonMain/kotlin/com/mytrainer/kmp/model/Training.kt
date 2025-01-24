package com.mytrainer.kmp.model

data class Training(
    val id: String,
    val name: String,
    val recoveryDurationD: Long,
    val exercises: List<Exercise>
) {
    val estimatedDurationS: Long
        get() = exercises.fold(0L, { accEx, ex ->
            accEx + ex.series.fold(0L, { accSeries, series ->
                val seriesDuration = when (series) {
                    is Series.RepSeries -> series.reps * 5L
                    is Series.DurationSeries -> series.durationS
                }
                accSeries + seriesDuration
            }) + 5*60
        })

    fun getRecoveryFor(exerciseIndex: Int) = exercises.getOrNull(exerciseIndex)?.recoveryDurationS
}

fun trainingFromId(id: String): Training {
    return when (id) {
        "PUSH_TRAINING" -> PUSH_TRAINING
        "PULL_TRAINING" -> PULL_TRAINING
        "LEGS_TRAINING" -> LEGS_TRAINING
        else -> TEST_TRAINING
    }
}

val PUSH_TRAINING = Training(
    id = "PUSH_TRAINING",
    name = "Exercises Push",
    recoveryDurationD = 2,
    exercises = listOf(
        Exercise(
            name = "Dévelopé couché haltères",
            series = DEGRESSIVE_REP_SERIES,
            recoveryDurationS = 120
        ),
        Exercise(
            name = "Dévelopé couché incliné",
            series = CONSTANT_REP_SERIES_10,
            recoveryDurationS = 120
        ),
        Exercise(
            name = "Tractions assitées",
            series = CONSTANT_REP_SERIES_12,
            recoveryDurationS = 60
        ),
        Exercise(
            name = "Dévelopé militaire",
            series = CONSTANT_REP_SERIES_10,
            recoveryDurationS = 120
        ),
        Exercise(
            name = "Extension poulie triceps",
            series = CONSTANT_REP_SERIES_12,
            recoveryDurationS = 60
        ),
        Exercise(
            name = "Crunch",
            series = CONSTANT_DURATION_SERIES_60,
            recoveryDurationS = 20
        )
    )
)

val PULL_TRAINING = Training(
    id = "PULL_TRAINING",
    name = "Exercises Pull",
    recoveryDurationD = 2,
    exercises = listOf(
        Exercise(
            name = "Tirage vertical",
            series = DEGRESSIVE_REP_SERIES,
            recoveryDurationS = 120
        ),
        Exercise(
            name = "Low row prise serrée",
            series = CONSTANT_REP_SERIES_8,
            recoveryDurationS = 90
        ),
        Exercise(
            name = "Low row prise large",
            series = CONSTANT_REP_SERIES_10,
            recoveryDurationS = 60
        ),
        Exercise(
            name = "Pull down",
            series = CONSTANT_REP_SERIES_8,
            recoveryDurationS = 90
        ),
        Exercise(
            name = "Soulevé de terre",
            series = CONSTANT_REP_SERIES_12,
            recoveryDurationS = 45
        ),
        Exercise(
            name = "Curl mateau haltères",
            series = CONSTANT_REP_SERIES_10,
            recoveryDurationS = 60
        )
    )
)

val LEGS_TRAINING = Training(
    id = "LEGS_TRAINING",
    name = "Exercises Legs",
    recoveryDurationD = 2,
    exercises = listOf(
        Exercise(
            name = "Squat cage guidée",
            series = DEGRESSIVE_REP_SERIES,
            recoveryDurationS = 120
        ),
        Exercise(
            name = "Leg press",
            series = CONSTANT_REP_SERIES_10,
            recoveryDurationS = 120
        ),
        Exercise(
            name = "Fentes",
            series = CONSTANT_REP_SERIES_12,
            recoveryDurationS = 90
        ),
        Exercise(
            name = "Leg extension",
            series = CONSTANT_REP_SERIES_10,
            recoveryDurationS = 90
        ),
        Exercise(
            name = "Leg curl",
            series = CONSTANT_REP_SERIES_10,
            recoveryDurationS = 90
        ),
        Exercise(
            name = "Gainnage",
            series = CONSTANT_DURATION_SERIES_60,
            recoveryDurationS = 30
        )
    )
)

val TEST_TRAINING = Training(
    id = "TEST_TRAINING",
    name = "Exercises de test",
    recoveryDurationD = 2,
    exercises = listOf(
        Exercise(
            name = "Sauter",
            series = DEGRESSIVE_REP_SERIES,
            recoveryDurationS = 2
        ),
        Exercise(
            name = "Pompes",
            series = CONSTANT_REP_SERIES_8,
            recoveryDurationS = 2
        ),
        Exercise(
            name = "Marcher",
            series = CONSTANT_DURATION_SERIES_60,
            recoveryDurationS = 2
        ),
    )
)