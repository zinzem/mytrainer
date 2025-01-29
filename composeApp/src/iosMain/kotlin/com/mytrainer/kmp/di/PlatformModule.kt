package com.mytrainer.kmp.di

import com.mytrainer.kmp.datasource.local.LocalDatabase
import com.mytrainer.kmp.datasource.local.buildAppDatabase
import com.mytrainer.kmp.manager.TextToSpeechManager
import com.mytrainer.kmp.manager.iOSTextToSpeechManager
import org.koin.dsl.module

actual val platformModule = module {

    single<LocalDatabase> { buildAppDatabase() }

    factory<TextToSpeechManager> { iOSTextToSpeechManager() }
}