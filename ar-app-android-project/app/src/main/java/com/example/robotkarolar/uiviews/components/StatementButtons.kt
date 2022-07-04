package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression
import com.example.robotkarolar.karollogic.instructions.expressions.IsBorder
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun StatementButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4)
    ) {
        item { AddButton({ viewModel.addInstruction(Step()) }, "STEP") }
        item { AddButton({ viewModel.addInstruction(Place()) }, "PLACE") }
        item { AddButton({ viewModel.addInstruction(Lift()) }, "LIFT") }
        item { AddButton({ viewModel.addInstruction(LeftTurn()) }, "LEFTTURN") }
        item { AddButton({ viewModel.addInstruction(RightTurn()) }, "RIGHTTURN") }
        item { AddButton({ viewModel.addInstruction(End()) }, "END") }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddButton(lambda: () -> Unit ,buttonName: String) {
    Button(onClick = lambda) {
        Text(text = buttonName)
    }
}