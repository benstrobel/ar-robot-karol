package com.example.robotkarolar.uiviews.components.bars.bottomBar

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.robotkarolar.R

sealed class Screen(
    val id: String,
    val title: String,
    val icon: ImageVector
) {
    object Free: Screen("free", "CodeEditor", Icons.Outlined.SmartToy)
    object Challenge: Screen("challenge", "Challenge", Icons.Outlined.Flag)

    object Items {
        val list = listOf(
            Free, Challenge
        )
    }
}
