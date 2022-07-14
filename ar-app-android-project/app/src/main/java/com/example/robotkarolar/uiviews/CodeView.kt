package com.example.robotkarolar.uiviews

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic.instructions.statements.Place
import com.example.robotkarolar.karollogic.instructions.statements.Step
import com.example.robotkarolar.uiviews.components.*
import com.example.robotkarolar.uiviews.components.AddField.OperatorButtons
import com.example.robotkarolar.uiviews.components.bars.AddOptionsBar
import com.example.robotkarolar.uiviews.components.bars.BottomNavBar
import com.example.robotkarolar.uiviews.models.AddFieldStates
import com.example.robotkarolar.uiviews.models.CodeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
@ExperimentalMaterialApi
fun CodeView(navController: NavController, viewModel: CodeViewModel) {
    Column(modifier = Modifier.padding(5.dp)) {
        BottomNavBar(
            navController = navController,
            currentScreenId = viewModel.currentScreen.value.id,
            onItemSelected = {viewModel.currentScreen.value = it}
        )
        CodeBlockPrev(viewModel = viewModel)
        CodeAddField(viewModel = viewModel)
    }
}

@Composable
@ExperimentalMaterialApi
fun CodeBlockPrev(viewModel: CodeViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.6f)
        .padding(5.dp)
        .border(
            width = 1.dp,
            color = MaterialTheme.colors.primaryVariant,
            shape = RoundedCornerShape(5.dp)
        )
        .clip(RoundedCornerShape(5.dp))
        .background(MaterialTheme.colors.primaryVariant.copy(0.5f))
        .padding(5.dp)
    ) {
        Column() {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .padding(5.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(5.dp)
                )
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colors.primary.copy(1.0f))
                .padding(5.dp)
                .verticalScroll(rememberScrollState())
                .horizontalScroll(rememberScrollState())
            ) {
                CodeRow(viewModel.root, viewModel)
            }

            CodeNavigatorBar(viewModel = viewModel)
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun CodeAddField(viewModel: CodeViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)
        .clip(RoundedCornerShape(5.dp))
        //.background(MaterialTheme.colors.primary)
        .padding(5.dp)
    ) {

        Column() {
            AddOptionsBar(viewModel = viewModel)

            when (viewModel.addFieldState.value) {
                AddFieldStates.Statements -> StatementButtons(viewModel = viewModel)
                AddFieldStates.ControllFlow -> ControllFlowButtons(viewModel = viewModel)
                AddFieldStates.Expressions -> ExpressionButtons(viewModel = viewModel)
                AddFieldStates.Operator -> OperatorButtons(viewModel = viewModel)
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun CodeViewPreview() {
    var viewModel = CodeViewModel()
    viewModel.addInstruction(LeftTurn())
    viewModel.addInstruction(Place())
    viewModel.addInstruction(Step())

    val navController = rememberNavController()

    CodeView(viewModel = viewModel, navController = navController)
}