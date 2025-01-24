package com.mytrainer.kmp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mytrainer.kmp.extension.FormatStyle
import com.mytrainer.kmp.ui.theme.MyTrainerTheme
import com.mytrainer.kmp.extension.count
import com.mytrainer.kmp.extension.formatDuration
import com.mytrainer.kmp.extension.toFormattedDate
import com.mytrainer.kmp.model.Training
import kmm_picsplash.composeapp.generated.resources.Res
import kmm_picsplash.composeapp.generated.resources.home_last_session_date
import kmm_picsplash.composeapp.generated.resources.home_last_session_never
import kmm_picsplash.composeapp.generated.resources.ic_calendar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    startTraining: (Training) -> Unit
) {
    val homeState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        launch {
            viewModel.events
                .onSubscription { viewModel.start() }
                .collectLatest {
                    when (it) {

                    }
                }
        }
    }

    HomeScreen(
        state = homeState,
        onTrainingClick = startTraining
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onTrainingClick: (Training) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Liste des Entrainements",
            style = MyTrainerTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Selectione un entrainement pour commencer",
            style = MyTrainerTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        state.trainings.forEach {
            Training(it.first, it.second, onTrainingClick)
        }
    }
}

@Composable
fun Training(training: Training, lastSession: Instant?, onClick: (Training) -> Unit) {
    Row(Modifier
        .padding(bottom = 16.dp)
        .clip(RoundedCornerShape(5.dp))
        .clickable { onClick(training) }
        .height(IntrinsicSize.Min)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .background(MyTrainerTheme.colors.primary)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MyTrainerTheme.colors.background)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = training.name,
                        style = MyTrainerTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "~${training.estimatedDurationS.formatDuration()}",
                        style = MyTrainerTheme.typography.titleSmall
                    )
                }
                Column(modifier = Modifier.padding(start = 16.dp, top = 5.dp)) {
                    training.exercises.forEach {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val count = it.series.joinToString(separator = "-") {
                                it.count().toString()
                            }
                            Text(text = "- ${it.name}", style = MyTrainerTheme.typography.bodyLarge)
                            Text(
                                text = count,
                                style = MyTrainerTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                    }
                }
            }
            Surface(color = MyTrainerTheme.colors.primary) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp)
                        .background(MyTrainerTheme.colors.primary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(Res.drawable.ic_calendar), contentDescription = null)
                    Text(
                        text = lastSession.toFormatedLastSessionDate(),
                        style = MyTrainerTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Instant?.toFormatedLastSessionDate(): String {
    return this?.let {
         stringResource(Res.string.home_last_session_date, toFormattedDate(FormatStyle.MEDIUM))
    } ?: stringResource(Res.string.home_last_session_never)
}

@Preview
@Composable
fun HomeScreenPreview() {
    MyTrainerTheme {
        Surface {
            HomeScreen(HomeState(), {})
        }
    }
}

/*@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkModePreview() {
    MyTrainerTheme {
        Surface {
            HomeScreen(HomeState(), {})
        }
    }
}*/
