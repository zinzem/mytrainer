package com.mytrainer.kmp.datasource.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_session")
data class LocalTrainingSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val trainingId: String,
    val durationS: Long,
    val createdAt: Long
)