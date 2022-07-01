package com.example.robotkarolar.uiviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.uiviews.components.CodeNavigator
import com.example.robotkarolar.uiviews.components.CodeRow
import com.example.robotkarolar.uiviews.components.CommandButtons
import com.example.robotkarolar.uiviews.components.ControllFlowButtons

@Composable
fun CodeView(viewModel: CodeViewModel) {
    Column(modifier = Modifier.padding(5.dp)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xBE9FA9B3))
            .padding(5.dp)
            .verticalScroll(rememberScrollState())
        ) {
            CodeRow(codeParts = viewModel.chain.value)
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
                CommandButtons(viewModel = viewModel)
                ControllFlowButtons(viewModel = viewModel)
            }
        }
    }
}

@Preview
@Composable
fun CodeViewPreview() {
    var viewModel = CodeViewModel()
    viewModel.addToCode(Command(CommandType.TURNRIGHT))
    viewModel.addToCode(Command(CommandType.TURN))
    viewModel.addToCode(Command(CommandType.STEP))

    CodeView(viewModel = viewModel)
}