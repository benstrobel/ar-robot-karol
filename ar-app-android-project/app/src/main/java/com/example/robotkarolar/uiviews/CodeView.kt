package com.example.robotkarolar.uiviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic.instructions.statements.Place
import com.example.robotkarolar.karollogic.instructions.statements.Step
import com.example.robotkarolar.uiviews.components.*

@Composable
@ExperimentalMaterialApi
fun CodeView(viewModel: CodeViewModel) {
    Column(modifier = Modifier.padding(5.dp)) {

        Text(text = "RoboterCarolAR")
        
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xBE9FA9B3))
            .padding(5.dp)
            .verticalScroll(rememberScrollState())
        ) {
            CodeRow(viewModel.root, viewModel)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
        ) {
            CodeNavigator(viewModel = viewModel)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xBE9FA9B3))
            .padding(5.dp)
            .verticalScroll(rememberScrollState())
        ) {
            Column() {
                if(viewModel.cursor.value is EmptyExpression) {
                    ExpressionButtons(viewModel = viewModel)
                } else {
                    StatementButtons(viewModel = viewModel)
                    ControllFlowButtons(viewModel = viewModel)
                }

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

    CodeView(viewModel = viewModel)
}