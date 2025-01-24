package com.mytrainer.kmp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

private val LocalColors = compositionLocalOf<MyTrainerColors> { error("No Colors provided") }

object MyTrainerTheme {
    val colors: MyTrainerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = Typography
}

@Composable
fun MyTrainerTheme(
    colors: MyTrainerColors = myTrainerColors(),
    typography: Typography = Typography,
    content: @Composable () -> Unit
) {
    /*val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity  = view.context as Activity
            activity.window.navigationBarColor = colorScheme.primary.copy(alpha = 0.08f)
                .compositeOver(colorScheme.surface.copy())
                .toArgb()
            activity.window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }*/

    CompositionLocalProvider(
        LocalColors provides myTrainerColors(),
    ) {
        content()
        MaterialTheme(
            colorScheme = colors.material,
            typography = typography,
            content = content
        )
    }
}