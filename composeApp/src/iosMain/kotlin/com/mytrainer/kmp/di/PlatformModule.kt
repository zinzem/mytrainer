package com.mytrainer.kmp.di

import com.mytrainer.kmp.datasource.local.LocalDatabase
import com.mytrainer.kmp.datasource.local.buildAppDatabase
import org.koin.dsl.module

actual val platformModule = module {

    single<LocalDatabase> { buildAppDatabase() }
}