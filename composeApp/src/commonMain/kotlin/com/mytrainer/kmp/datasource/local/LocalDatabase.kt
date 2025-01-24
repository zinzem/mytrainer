package com.mytrainer.kmp.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mytrainer.kmp.datasource.local.dao.TrainingSessionDao
import com.mytrainer.kmp.datasource.local.model.LocalTrainingSession

@Database(entities = [LocalTrainingSession::class], version = 1)
abstract class LocalDatabase : RoomDatabase(), DB {
    abstract fun trainingSessionDao(): TrainingSessionDao
    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}