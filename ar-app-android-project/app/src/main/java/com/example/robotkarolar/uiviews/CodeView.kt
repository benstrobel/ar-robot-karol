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
import com.example.robotkarolar.karollogic_ben.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic_ben.instructions.statements.Place
import com.example.robotkarolar.karollogic_ben.instructions.statements.Step
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.uiviews.components.CodeNavigator
import com.example.robotkarolar.uiviews.components.CodeRow
import com.example.robotkarolar.uiviews.components.StatementButtons
import com.example.robotkarolar.uiviews.components.ControllFlowButtons

@Composable
@ExperimentalMaterialApi
fun CodeView(viewModel: CodeViewModel2) {
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
            CodeRow(viewModel.codeBlock.value, viewModel.cursor)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
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
                StatementButtons(viewModel = viewModel)
                ControllFlowButtons(viewModel = viewModel)
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun CodeViewPreview() {
    var viewModel = CodeViewModel2()
    viewModel.addInstruction(LeftTurn())
    viewModel.addInstruction(Place())
    viewModel.addInstruction(Step())

    CodeView(viewModel = viewModel)
}