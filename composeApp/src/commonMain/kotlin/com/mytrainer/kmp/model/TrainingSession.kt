package com.mytrainer.kmp.model

import com.mytrainer.kmp.datasource.local.model.LocalTrainingSession
import kotlinx.datetime.Instant

data class TrainingSession(
    val id: Long,
    val training: Training,
    val durationS: Long,
    val createdAt: Instant
)

fun LocalTrainingSession.toTrainingSession() = TrainingSession(
    id = id,
    training = trainingFromId(trainingId),
    durationS = durationS,
    createdAt = Instant.fromEpochMilliseconds(createdAt)
)