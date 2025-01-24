package com.mytrainer.kmp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mytrainer.kmp.ui.training.TrainingRoute
import org.koin.compose.viewmodel.koinViewModel

const val TRAINING_GRAPH = "trainingGraph"
private const val TRAINING_ROUTE = "training?sessionId={sessionId}&trainingId={trainingId}&exerciseIndex={exerciseIndex}"

fun NavGraphBuilder.trainingGraph(
    goToNext: (Long, String, Int) -> Unit,
    finishTraining: () -> Unit
) {
  navigation(startDestination = TRAINING_ROUTE, route = TRAINING_GRAPH) {
    composable(route = TRAINING_ROUTE) {
      val sessionId = it.arguments?.getString("sessionId")?.toLong()
      val trainingId = it.arguments?.getString("trainingId")
      val exerciseIndex = it.arguments?.getString("exerciseIndex")?.toInt() ?: 0
      TrainingRoute(
        viewModel = koinViewModel(),
        sessionId = sessionId,
        trainingId = trainingId,
        exerciseIndex = exerciseIndex,
        goToNext = goToNext,
        finishTraining = finishTraining
      )
    }
  }
}

fun trainingRoute(
  sessionId: Long? = null,
  trainingId: String,
  exerciseIndex: Int,
): String {
  return sessionId?.let {
    "training?sessionId=$sessionId&trainingId=$trainingId&exerciseIndex=$exerciseIndex"
  } ?: "training?trainingId=$trainingId&exerciseIndex=$exerciseIndex"
}