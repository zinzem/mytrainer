package com.mytrainer.kmp.repository

import com.mytrainer.kmp.datasource.local.LocalDatabase
import com.mytrainer.kmp.datasource.local.model.LocalTrainingSession
import com.mytrainer.kmp.model.toTrainingSession
import kotlinx.datetime.Clock

class TrainingRepository(
    private val db: LocalDatabase
) {

    suspend fun startSession(trainingId: String): Long {
        return db.trainingSessionDao().create(
            LocalTrainingSession(
                trainingId = trainingId,
                durationS = 0,
                createdAt = Clock.System.now().toEpochMilliseconds()
            )
        )
    }

    suspend fun getSessions() = db.trainingSessionDao().getAll()
        .map { it.toTrainingSession() }

    suspend fun endSession(sessionId: Long) {
        db.trainingSessionDao().getById(sessionId)?.let {
            val duration = Clock.System.now().epochSeconds - (it.createdAt / 1000)
            db.trainingSessionDao().update(it.copy(durationS = duration))
        } ?: {
            // TODO Handle error
        }
    }
}