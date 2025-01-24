package com.mytrainer.kmp.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mytrainer.kmp.datasource.local.model.LocalTrainingSession

@Dao
interface TrainingSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(trainingSession: LocalTrainingSession): Long

    @Query("SELECT * FROM training_session")
    suspend fun getAll(): List<LocalTrainingSession>

    @Query("SELECT * FROM training_session WHERE id = :id")
    suspend fun getById(id: Long): LocalTrainingSession?

    @Update
    suspend fun update(trainingSession: LocalTrainingSession)

    @Query("DELETE FROM training_session WHERE id = :id")
    suspend fun deleteById(id: Long)
}