package be.mbolle.mealplanner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightColorScheme = lightColorScheme(
    primaryContainer = upperrSoftYellow,
    secondaryContainer = darkYellow,
    tertiaryContainer = grayYellowForeground,
    background = mildSoftYellow,
    outline = darkYellow,
)

@Composable
fun MealPlannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography,
        content = content
    )
}