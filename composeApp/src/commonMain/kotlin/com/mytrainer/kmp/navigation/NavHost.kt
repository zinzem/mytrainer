package com.mytrainer.kmp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MyTrainerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_GRAPH,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier
    ) {

        splashGraph(navigateToHome = { navController.navigateToTopLevelScreen(HOME_GRAPH) })

        homeGraph(startTraining = {
            navController.navigate(trainingRoute(trainingId = it.id, exerciseIndex = 0))
        })

        trainingGraph(
            goToNext = { sessionId, trainingId, exerciseIndex ->
                navController.navigateToTopLevelScreen(
                    to = trainingRoute(sessionId, trainingId , exerciseIndex)
                )
            },
            finishTraining = { navController.navigateUp() }
        )
    }
}

fun NavController.navigateToTopLevelScreen(to: String) {
    //val from = currentDestination?.id
    val from = currentDestination?.route
    navigate(to) {
        from?.let {
            popUpTo(from) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}

/*fun NavController.navigateToTabBarScreen(to: String) {
    navigate(to) {
        popUpTo(homeRoute()) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.setNavigationResult(data: Map<String, Any?>?) {
    previousBackStackEntry?.savedStateHandle?.let {
        data?.forEach { (key, value) ->
            it[key] = value
        }
    }
}*/
