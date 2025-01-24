package com.mytrainer.kmp.ui.home

import androidx.lifecycle.viewModelScope
import com.mytrainer.kmp.ui.util.BaseViewModel
import com.mytrainer.kmp.model.LEGS_TRAINING
import com.mytrainer.kmp.model.PULL_TRAINING
import com.mytrainer.kmp.model.PUSH_TRAINING
import com.mytrainer.kmp.model.TEST_TRAINING
import com.mytrainer.kmp.model.Training
import com.mytrainer.kmp.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant

class HomeViewModel(
    private val trainingRepository: TrainingRepository
) : BaseViewModel<HomeState>(HomeState()) {

    override fun onStart(args: List<Any?>) {
        viewModelScope.launch(Dispatchers.IO) {
            val trainingsWithLastSession = trainingRepository.getSessions()
                .groupBy { it.training }
                .mapValues { it.value.filter { it.durationS > 0 }.maxOfOrNull { v -> v.createdAt } }
            setState { copy(trainings = trainings.map {
                it.first to trainingsWithLastSession.getOrElse(it.first, { null})
            }) }
        }
    }
}

data class HomeState(
    val trainings: List<Pair<Training, Instant?>> = listOf(
        PUSH_TRAINING to null,
        PULL_TRAINING to null,
        LEGS_TRAINING to null,
        TEST_TRAINING to null
    )
)