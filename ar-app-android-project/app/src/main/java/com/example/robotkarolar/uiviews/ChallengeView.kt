package com.example.robotkarolar.uiviews

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.robotkarolar.uiviews.components.bars.BottomNavBar
import com.example.robotkarolar.uiviews.models.CodeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChallengeView(navController: NavController, viewModel: CodeViewModel) {
    Scaffold(
        topBar = { Toolbar(topBarText = "ArRobotKarol") },
        bottomBar = { BottomNavBar(
            navController = navController,
            currentScreenId = viewModel.currentScreen.value.id,
            onItemSelected = {viewModel.currentScreen.value = it}
        )
        }
    ) {
    }
}