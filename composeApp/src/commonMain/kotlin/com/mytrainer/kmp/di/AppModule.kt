package com.mytrainer.kmp.di

import com.mytrainer.kmp.ui.home.HomeViewModel
import com.mytrainer.kmp.ui.training.TrainingViewModel
import com.mytrainer.kmp.repository.TrainingRepository
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {

    factoryOf(::TrainingRepository)

    viewModelOf(::HomeViewModel)
    viewModelOf(::TrainingViewModel)
}
