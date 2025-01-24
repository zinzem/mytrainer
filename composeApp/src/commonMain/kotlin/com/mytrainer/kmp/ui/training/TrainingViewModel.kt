package com.mytrainer.kmp.ui.training

import androidx.lifecycle.viewModelScope
import com.mytrainer.kmp.ui.util.BaseViewModel
import com.mytrainer.kmp.ui.util.Event
import com.mytrainer.kmp.model.LEGS_TRAINING
import com.mytrainer.kmp.model.PULL_TRAINING
import com.mytrainer.kmp.model.PUSH_TRAINING
import com.mytrainer.kmp.model.TEST_TRAINING
import com.mytrainer.kmp.model.Training
import com.mytrainer.kmp.repository.TrainingRepository
import com.mytrainer.kmp.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TrainingViewModel(
    //private val textToSpeechManager: TextToSpeechManager,
    //private val resourceManager: ResourceManager
    private val trainingRepository: TrainingRepository
) : BaseViewModel<TrainingState>(TrainingState()) {

    private var clockJob: Job? = null

    override fun onStart(args: List<Any?>) {
        val sessionId = args.getOrNull(0) as? Long
        val trainingId = args.getOrNull(1) as? String
        val exerciseIndex = args.getOrNull(2) as Int

        if (trainingId == null) {
            // TODO Handle Error
        } else {
            when (trainingId) {
                PUSH_TRAINING.id -> setState { copy(training = PUSH_TRAINING) }
                PULL_TRAINING.id -> setState { copy(training = PULL_TRAINING) }
                LEGS_TRAINING.id -> setState { copy(training = LEGS_TRAINING) }
                TEST_TRAINING.id -> setState { copy(training = TEST_TRAINING) }
            }
            setState {
                val recoveryDuration = training?.getRecoveryFor(exerciseIndex) ?: 0
                copy(
                    currentExercise = exerciseIndex,
                    currentSeries = 0,
                    recoveryDuration = recoveryDuration,
                    recoveryTimeLeft = recoveryDuration
                )
            }

            if (exerciseIndex == 0) {
                viewModelScope.launch {
                    val newSessionId = trainingRepository.startSession(trainingId)
                    setState { copy(sessionId = newSessionId) }
                    Log.e("${state.value.sessionId}: ex $exerciseIndex (start)")
                }
            } else {
                Log.e("$sessionId: ex $exerciseIndex")
                setState { copy(sessionId = sessionId) }
            }
        }
    }

    fun onToggleMute() = setState { copy(isMuted = !isMuted) }

    fun onCompletedClick() {
        val maxSeriesIndex = state.value.training?.exercises
            ?.getOrNull(state.value.currentExercise)
            ?.series
            ?.lastIndex ?: 0
        val maxExerciseIndex = state.value.training?.exercises?.lastIndex ?: 0
        val recoveryTimeS = state.value.training?.exercises
            ?.getOrNull(state.value.currentExercise)
            ?.recoveryDurationS ?: 0

        if (state.value.currentSeries >= maxSeriesIndex
            && state.value.currentExercise >= maxExerciseIndex) {
            viewModelScope.launch(Dispatchers.IO) {
                state.value.sessionId?.let {
                    trainingRepository.endSession(it)
                }
            }
            sendEvent(TrainingFinished)
        } else {
            setState { copy(recovery = true) }
            clockJob = viewModelScope.launch {
                (0..<recoveryTimeS)
                    .asSequence()
                    .asFlow()
                    .onEach {
                        /*if (!state.value.isMuted) {
                            when {
                                state.value.recoveryTimeLeft == 30L -> {
                                    textToSpeechManager.say(
                                        resourceManager.getString(R.string.recovery_30s_left)
                                    )
                                }
                                state.value.recoveryTimeLeft <= 5 -> {
                                    textToSpeechManager.say(state.value.recoveryTimeLeft.toString())
                                }
                            }
                        }*/
                        delay(1000)
                        setState { copy(recoveryTimeLeft = recoveryTimeLeft - 1) }
                    }
                    .onCompletion {
                        if (state.value.currentSeries >= maxSeriesIndex) {
                            Log.e("${state.value.sessionId}: going to next ex")
                            sendEvent(
                                GoToNext(
                                    sessionId = state.value.sessionId!!,
                                    trainingId = state.value.training?.id!!,
                                    exerciseIndex = state.value.currentExercise + 1
                                )
                            )
                        } else {
                            setState {
                                copy(
                                    recovery = false,
                                    currentSeries = currentSeries + 1,
                                    recoveryTimeLeft = recoveryDuration
                                )
                            }
                        }
                    }.collect()
            }
        }
    }

    fun onSkipClick() {
        clockJob?.cancel()
    }
}

data class TrainingState(
    val sessionId: Long? = null,
    val training: Training? = null,
    val currentExercise: Int = 0,
    val currentSeries: Int = 0,
    val recovery: Boolean = false,
    val recoveryDuration: Long = 0,
    val recoveryTimeLeft: Long = 0,
    val isMuted: Boolean = false
)

data class GoToNext(
    val sessionId: Long,
    val trainingId: String,
    val exerciseIndex: Int
) : Event

data object TrainingFinished : Event