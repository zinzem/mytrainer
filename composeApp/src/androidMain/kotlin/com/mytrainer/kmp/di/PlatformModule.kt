package com.mytrainer.kmp.di

import com.mytrainer.kmp.datasource.local.LocalDatabase
import com.mytrainer.kmp.datasource.local.buildAppDatabase
import com.mytrainer.kmp.manager.AndroidTextToSpeechManager
import com.mytrainer.kmp.manager.TextToSpeechManager
import org.koin.dsl.module

actual val platformModule = module {

    single<LocalDatabase> { buildAppDatabase(get()) }

    factory<TextToSpeechManager> { AndroidTextToSpeechManager(get()) }
}