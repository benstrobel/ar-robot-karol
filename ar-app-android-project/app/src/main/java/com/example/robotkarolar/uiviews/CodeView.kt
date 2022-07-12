package com.example.robotkarolar.uiviews

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.ArActivity
import com.example.robotkarolar.ar.ArCommand
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic.instructions.statements.Place
import com.example.robotkarolar.karollogic.instructions.statements.Step
import com.example.robotkarolar.uiviews.components.*
import com.example.robotkarolar.uiviews.components.buttons.ArButton

@Composable
@ExperimentalMaterialApi
fun CodeView(viewModel: CodeViewModel) {
    Column(modifier = Modifier.padding(5.dp)) {

        CodeBlockPrev(viewModel = viewModel)

        CodeAdd(viewModel = viewModel)
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
            ) {
                CodeRow(viewModel.root, viewModel)
            }

            CodeNavigatorBar(viewModel = viewModel)
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun CodeAdd(viewModel: CodeViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)
        .clip(RoundedCornerShape(5.dp))
        .background(Color(0xBE9FA9B3))
        .padding(5.dp)
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