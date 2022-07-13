package com.example.robotkarolar.uiviews

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
import com.example.robotkarolar.R
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic.instructions.statements.Place
import com.example.robotkarolar.karollogic.instructions.statements.Step
import com.example.robotkarolar.uiviews.models.CodeViewModel

@ExperimentalMaterialApi
@Composable
fun HomeView(viewModel: CodeViewModel) {
    Scaffold(
        topBar = { Toolbar(topBarText = "ArRoboterKarol") },
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

    HomeView(viewModel = viewModel)
}