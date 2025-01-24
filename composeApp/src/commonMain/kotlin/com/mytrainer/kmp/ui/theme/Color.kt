package com.mytrainer.kmp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFFF084B)
val Surface = Color(0xFFFFFFFF)
val Background = Color(0xFFffe4e2)
val Success = Color(0xFF05D5AA)
val Info = Color(0xFF3a86ff)

val PrimaryDark = Color(0xFFdb0033)
val SurfaceDark = Color(0xFF000000)
val BackgroundDark = Color(0xFF740000)
val SuccessDark = Color(0xFF80ed99)

val White = Color(0xFFFFFFFF)

data class MyTrainerColors(
    val material: ColorScheme,
    val positive: Color,
    val information: Color,
) {
    val primary: Color get() = material.primary
    val secondary: Color get() = material.secondary
    val tertiary: Color get() = material.tertiary
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
}

@Composable
@ReadOnlyComposable
fun myTrainerColors(
    darkTheme: Boolean = isSystemInDarkTheme()
) = if (darkTheme) myTrainerDarkColors() else myTrainerLightColors()

@Composable
@ReadOnlyComposable
fun myTrainerLightColors() = MyTrainerColors(
    material = lightColorScheme(
        primary = Primary,
        onPrimary = White,
        background = Background,
        surface = Surface,
        /*error = colorResource(id = R.color.error_light),
        onPrimary = colorResource(id = R.color.on_brand_primary_light),
        onSecondary = colorResource(id = R.color.on_brand_secondary_light),
        onBackground = colorResource(id = R.color.on_background_light),
        onSurface = colorResource(id = R.color.on_surface_light),
        onError = colorResource(id = R.color.on_error_light)*/
    ),
    positive = Success,
    information = Info
)

@Composable
@ReadOnlyComposable
private fun myTrainerDarkColors() = MyTrainerColors(
    material = darkColorScheme(
        primary = PrimaryDark,
        onPrimary = White,
        background = BackgroundDark,
        surface = SurfaceDark,
    ),
    positive = SuccessDark,
    information = Info
)