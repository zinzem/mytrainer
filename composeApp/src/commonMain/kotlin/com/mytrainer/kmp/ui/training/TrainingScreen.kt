package com.mytrainer.kmp.ui.training

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mytrainer.kmp.ui.theme.MyTrainerTheme
import com.mytrainer.kmp.model.CONSTANT_DURATION_SERIES_60
import com.mytrainer.kmp.model.CONSTANT_REP_SERIES_10
import com.mytrainer.kmp.model.CONSTANT_REP_SERIES_12
import com.mytrainer.kmp.model.DEGRESSIVE_REP_SERIES
import com.mytrainer.kmp.model.Exercise
import com.mytrainer.kmp.model.Series
import com.mytrainer.kmp.model.Training
import kmm_picsplash.composeapp.generated.resources.Res
import kmm_picsplash.composeapp.generated.resources.ic_check_circle
import kmm_picsplash.composeapp.generated.resources.ic_pause_circle
import kmm_picsplash.composeapp.generated.resources.ic_schedule
import kmm_picsplash.composeapp.generated.resources.ic_volume_off
import kmm_picsplash.composeapp.generated.resources.ic_volume_on
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TrainingRoute(
    viewModel: TrainingViewModel,
    sessionId: Long?,
    trainingId: String?,
    exerciseIndex: Int,
    goToNext: (Long, String, Int) -> Unit,
    finishTraining: () -> Unit
) {
    val trainingState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        launch {
            viewModel.events
                .onSubscription { viewModel.start(listOf(sessionId, trainingId, exerciseIndex)) }
                .collectLatest {
                    when (it) {
                        is GoToNext -> goToNext(
                            it.sessionId, it.trainingId, it.exerciseIndex
                        )
                        is TrainingFinished -> finishTraining()
                    }
                }
        }
    }

    Surface {
        TrainingScreen(
            state = trainingState,
            onToggleMute = viewModel::onToggleMute,
            onCompletedClick = viewModel::onCompletedClick,
            onSkipClick = viewModel::onSkipClick
        )
    }
}

@Composable
fun TrainingScreen(
    state: TrainingState,
    onToggleMute: () -> Unit,
    onCompletedClick: () -> Unit,
    onSkipClick: () -> Unit,
) {
    Column {
        LinearProgressIndicator(
            progress = {
                val done = state.training?.exercises?.foldIndexed(0, { i, acc, el->
                    if (i < state.currentExercise) {
                        acc + el.series.size
                    } else if (i == state.currentExercise) {
                        acc + state.currentSeries
                    } else acc
                }) ?: 0
                val max = state.training?.exercises?.fold(0, { acc, el -> acc + el.series.size }) ?: 0
                if (max == 0) 0f else done.toFloat() / max
            },
            gapSize = 0.dp,
            strokeCap = StrokeCap.Butt,
            drawStopIndicator = {},
            modifier = Modifier.fillMaxWidth()
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            val exercise = state.training?.exercises?.get(state.currentExercise)
            if (exercise != null) {
                Row {
                    Text(
                        text = state.training.name,
                        style = MyTrainerTheme.typography.headlineLarge,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .weight(1f)
                    )
                    TextButton(onClick = onToggleMute) {
                        Icon(
                            painter = painterResource(
                                if (state.isMuted) {
                                    Res.drawable.ic_volume_off
                                } else Res.drawable.ic_volume_on
                            ),
                            contentDescription = null)
                    }
                }
                state.training.exercises.forEachIndexed { index, ex ->
                    Text(
                        text = ex.name,
                        style = MyTrainerTheme.typography.headlineSmall,
                        color = MyTrainerTheme.colors.onSurface.copy(
                            alpha = if (index == state.currentExercise) 1f else 0.6f
                        )

                    )
                    if (index == state.currentExercise) {
                        Exercise(exercise, state.currentSeries, state.recovery)
                    }
                }
                if (state.recovery) {
                    Recovery(state.recoveryDuration, state.recoveryTimeLeft)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                if (!state.recovery) {
                    Button(onClick = onCompletedClick) {
                        Text(text = "J'ai finis", style = MyTrainerTheme.typography.titleLarge)
                    }
                } else {
                    Button(onClick = onSkipClick) {
                        Text(text = "Passer", style = MyTrainerTheme.typography.titleLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun Exercise(exercise: Exercise, currentSeriesIndex: Int, isRecovery: Boolean) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        exercise.series.forEachIndexed { index, it ->
            val seriesText = when (it) {
                is Series.RepSeries -> "Effectue ${it.reps} reps"
                is Series.DurationSeries -> "Effectue l'exercise pendant ${it.durationS} secondes"
            }
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .padding(10.dp)

            ) {
                when {
                    index == currentSeriesIndex -> {
                        if (isRecovery) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_pause_circle),
                                contentDescription = null,
                                modifier = Modifier.width(28.dp).height(28.dp),
                                tint = MyTrainerTheme.colors.primary
                            )
                        } else {
                            CircularProgressIndicator(
                                color = MyTrainerTheme.colors.primary,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        }

                    }
                    index < currentSeriesIndex -> {
                        Icon(
                            painter = painterResource(Res.drawable.ic_check_circle),
                            contentDescription = null,
                            modifier = Modifier.width(28.dp).height(28.dp),
                            tint = MyTrainerTheme.colors.positive
                        )
                    }
                    else -> {
                        Icon(
                            painter = painterResource(Res.drawable.ic_schedule),
                            contentDescription = null,
                            modifier = Modifier.width(28.dp).height(28.dp),
                            tint = MyTrainerTheme.colors.information
                        )
                    }
                }
                Text(
                    text = seriesText,
                    style = MyTrainerTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ColumnScope.Recovery(recoveryDuration: Long, secondsLeft: Long) {
    Text(
        text = "Repos, on repars dans:",
        style = MyTrainerTheme.typography.headlineSmall,
        modifier = Modifier
            .padding(top = 16.dp)
            .align(Alignment.CenterHorizontally)
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(top = 16.dp)
            .align(Alignment.CenterHorizontally)
    ) {
        Text(
            text = "$secondsLeft",
            style = MyTrainerTheme.typography.displayLarge,
            modifier = Modifier
        )
        CircularProgressIndicator(
            progress = { secondsLeft.toFloat() / recoveryDuration },
            strokeWidth = 10.dp,
            gapSize = 0.dp,
            strokeCap = StrokeCap.Square,
            modifier = Modifier.width(150.dp).height(150.dp)
        )
    }

}

@Preview
@Composable
fun TrainingScreenRepsPreview() {
    MyTrainerTheme {
        Surface {
            TrainingScreen(
                TrainingState(
                training = Training(
                    id = "PUSH_TRAINING",
                    name = "Exercises Push",
                    recoveryDurationD = 2,
                    exercises = listOf(
                        Exercise(
                            name = "Dévelopé couché haltères",
                            series = DEGRESSIVE_REP_SERIES,
                            recoveryDurationS = 120
                        ),
                        Exercise(
                            name = "Dévelopé couché incliné",
                            series = CONSTANT_REP_SERIES_10,
                            recoveryDurationS = 120
                        ),
                        Exercise(
                            name = "Tractions assitées",
                            series = CONSTANT_REP_SERIES_12,
                            recoveryDurationS = 60
                        )
                    )
                ),
                currentExercise = 1
            ), {}, {}, {})
        }
    }
}


@Preview
@Composable
fun TrainingScreenDurationPreview() {
    MyTrainerTheme {
        Surface {
            TrainingScreen(
                TrainingState(
                training = Training(
                    id = "PUSH_TRAINING",
                    name = "Exercises Push",
                    recoveryDurationD = 2,
                    exercises = listOf(
                        Exercise(
                            name = "Dévelopé couché haltères",
                            series = CONSTANT_DURATION_SERIES_60,
                            recoveryDurationS = 120
                        )
                    )
                )
            ), {}, {}, {})
        }
    }
}

@Preview
@Composable
fun TrainingRecoveryScreenPreview() {
    MyTrainerTheme {
        Surface {
            TrainingScreen(
                TrainingState(
                training = Training(
                    id = "PUSH_TRAINING",
                    name = "Exercises Push",
                    recoveryDurationD = 2,
                    exercises = listOf(
                        Exercise(
                            name = "Dévelopé couché haltères",
                            series = DEGRESSIVE_REP_SERIES,
                            recoveryDurationS = 120
                        )
                    )
                ),
                recovery = true
            ), {}, {}, {})
        }
    }
}

/*@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TrainingRecoveryScreenDarkModePreview() {
    MyTrainerTheme {
        Surface {
            TrainingScreen(
                TrainingState(
                training = Training(
                    id = "PUSH_TRAINING",
                    name = "Exercises Push",
                    exercises = listOf(
                        Exercise(
                            name = "Dévelopé couché haltères",
                            series = DEGRESSIVE_REP_SERIES,
                            recoveryDurationS = 120
                        )
                    )
                ),
                recovery = true
            ), {}, {}, {})
        }
    }
}*/
