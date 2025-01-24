package com.mytrainer.kmp.datasource.local

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun buildAppDatabase(): LocalDatabase {
    val dbFile = NSHomeDirectory() + "/kmm-pic-splash-app.db"
    return Room.databaseBuilder<LocalDatabase>(
        name = dbFile,
        factory = { LocalDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        //.fallbackToDestructiveMigration(true)
        .build()
}