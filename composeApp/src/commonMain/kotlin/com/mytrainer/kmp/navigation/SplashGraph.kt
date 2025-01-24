package com.mytrainer.kmp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mytrainer.kmp.ui.splash.SplashRoute

const val SPLASH_GRAPH = "splashGraph"
const val SPLASH_ROUTE = "splash"

fun NavGraphBuilder.splashGraph(navigateToHome: () -> Unit) {
  navigation(startDestination = SPLASH_ROUTE, route = SPLASH_GRAPH) {
    composable(route = SPLASH_ROUTE) {
      SplashRoute(navigateToHome = { navigateToHome() },)
    }
  }
}
