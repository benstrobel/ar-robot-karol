package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic_ben.instructions.statements.*
import com.example.robotkarolar.uiviews.CodeViewModel2

@Composable
fun StatementButtons(viewModel: CodeViewModel2) {
    Column() {
        AddButton({ viewModel.addInstruction(Step()) }, "STEP")
        AddButton({ viewModel.addInstruction(Place()) }, "PLACE")
        AddButton({ viewModel.addInstruction(Lift()) }, "LIFT")
        AddButton({ viewModel.addInstruction(LeftTurn()) }, "LEFTTURN")
        AddButton({ viewModel.addInstruction(RightTurn()) }, "RIGHTTURN")
        AddButton({ viewModel.addInstruction(End()) }, "END")
    }
}

@Composable
fun AddButton(lambda: () -> Unit ,buttonName: String) {
    Button( onClick = lambda) {
        Text(text = buttonName)
    }
}