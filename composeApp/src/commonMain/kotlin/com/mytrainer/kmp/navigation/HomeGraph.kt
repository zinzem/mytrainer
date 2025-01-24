package com.mytrainer.kmp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mytrainer.kmp.model.Training
import com.mytrainer.kmp.ui.home.HomeRoute
import org.koin.compose.viewmodel.koinViewModel

const val HOME_GRAPH = "homeGraph"
const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeGraph(
  startTraining: (Training) -> Unit
) {
  navigation(startDestination = HOME_ROUTE, route = HOME_GRAPH) {
    composable(route = HOME_ROUTE) {
      HomeRoute(
        viewModel = koinViewModel(),
        startTraining = startTraining)
    }
  }
}