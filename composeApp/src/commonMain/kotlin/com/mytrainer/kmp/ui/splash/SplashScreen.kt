package com.mytrainer.kmp.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SplashRoute(navigateToHome: () -> Unit) {
    HaSplashScreen(navigateToHome)
}

@Composable
fun HaSplashScreen(
    navigateToHome: () -> Unit,
) {
    LaunchedEffect(Unit) {
        navigateToHome()
    }
}