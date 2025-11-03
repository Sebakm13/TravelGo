package com.travelgo.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🌞 Paleta clara (texto negro)
private val LightColorScheme = lightColorScheme(
    primary = VerdePrimario,
    secondary = VerdeSecundario,
    background = FondoSuave,
    surface = CardColor,
    onPrimary = TextoBoton,
    onSecondary = TextoBoton,
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal
)

// 🌙 Paleta oscura (texto claro)
private val DarkColorScheme = darkColorScheme(
    primary = VerdeSecundario,
    secondary = VerdePrimario,
    background = DarkBg,
    surface = DarkCard,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary
)

@Composable
fun TravelGoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
