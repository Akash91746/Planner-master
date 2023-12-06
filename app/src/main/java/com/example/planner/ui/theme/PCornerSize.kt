package com.example.planner.ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PCornerSize(
    val default: CornerSize = CornerSize(0.dp),
    val extraSmall: CornerSize = CornerSize(2.dp),
    val small: CornerSize = CornerSize(4.dp),
    val medium: CornerSize = CornerSize(6.dp),
    val large: CornerSize = CornerSize(8.dp),
    val extraLarge: CornerSize = CornerSize(10.dp),
    val custom: (value: Dp) -> CornerSize = { CornerSize(it) }
)

internal val LocalCornerSize = staticCompositionLocalOf { PCornerSize() }

val MaterialTheme.cornerSize: PCornerSize
    @Composable
    @ReadOnlyComposable
    get() = LocalCornerSize.current