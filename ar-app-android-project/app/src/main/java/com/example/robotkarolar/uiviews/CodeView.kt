package com.example.robotkarolar.uiviews

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic.instructions.statements.Place
import com.example.robotkarolar.karollogic.instructions.statements.Step
import com.example.robotkarolar.uiviews.components.*
import com.example.robotkarolar.uiviews.models.AddFieldStates
import com.example.robotkarolar.uiviews.models.CodeViewModel

@Composable
@ExperimentalMaterialApi
fun CodeView(viewModel: CodeViewModel) {
    Column(modifier = Modifier.padding(5.dp)) {
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
        .clip(RoundedCornerShape(5.dp))
        .background(MaterialTheme.colors.primaryVariant)
        .padding(5.dp)
    ) {
        Column() {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colors.primary)
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
        when (viewModel.addFieldState.value) {
            AddFieldStates.Statements -> StatementButtons(viewModel = viewModel)
            AddFieldStates.ControllFlow -> ControllFlowButtons(viewModel = viewModel)
            AddFieldStates.Expressions -> ExpressionButtons(viewModel = viewModel)
        }

        /*Column() {
            if(viewModel.cursor.value is EmptyExpression) {
                ExpressionButtons(viewModel = viewModel)
            } else {
                StatementButtons(viewModel = viewModel)
                ControllFlowButtons(viewModel = viewModel)
            }

        }*/
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

    CodeView(viewModel = viewModel)
}