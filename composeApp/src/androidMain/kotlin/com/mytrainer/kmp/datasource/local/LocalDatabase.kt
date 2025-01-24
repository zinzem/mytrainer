package com.mytrainer.kmp.datasource.local

import android.content.Context
import androidx.room.Room

fun buildAppDatabase(context: Context): LocalDatabase {
    val dbFile = context.getDatabasePath("kmm-pic-splash-app.db")
    return Room.databaseBuilder<LocalDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        //.fallbackToDestructiveMigration(true)
        .build()
}