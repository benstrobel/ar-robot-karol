package com.example.robotkarolar.uiviews

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robotkarolar.R
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic.instructions.statements.Place
import com.example.robotkarolar.karollogic.instructions.statements.Step
import com.example.robotkarolar.uiviews.components.bars.BottomNavBar
import com.example.robotkarolar.uiviews.models.CodeViewModel

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun HomeView(navController: NavController, viewModel: CodeViewModel) {
    Scaffold(
        topBar = { Toolbar(topBarText = "ArRobotKarol") },
        bottomBar = { BottomNavBar(
            navController = navController,
            currentScreenId = viewModel.currentScreen.value.id,
            onItemSelected = {viewModel.currentScreen.value = it}
        )}
    ) {
        CodeView(viewModel = viewModel)
    }
}

@Composable
fun Toolbar(topBarText: String) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .padding(5.dp)
                    .size(30.dp)
                    .clip(RoundedCornerShape(0.dp))
            ) {
                Icon(painter = painterResource(id = R.drawable.robot), contentDescription = null)
            }

            Text(
                text = topBarText,
                style = MaterialTheme.typography.h1,
                //modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Box(
                Modifier
                    .padding(5.dp)
                    .size(30.dp)
                    .clip(RoundedCornerShape(0.dp))
            ) {
                Icon(painter = painterResource(id = R.drawable.robot), contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun HomeViewPreview() {
    var viewModel = CodeViewModel()
    viewModel.addInstruction(LeftTurn())
    viewModel.addInstruction(Place())
    viewModel.addInstruction(Step())

    val navController = rememberNavController()

    HomeView(navController = navController, viewModel = viewModel)
}