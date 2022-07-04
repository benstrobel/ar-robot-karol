package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression
import com.example.robotkarolar.karollogic.instructions.expressions.IsBorder
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun StatementButtons(viewModel: CodeViewModel) {
    Column() {
        if(viewModel.cursor.value is EmptyExpression){
            AddButton({viewModel.addInstruction(IsBorder())}, "ISBORDER")
        } else {
            AddButton({ viewModel.addInstruction(Step()) }, "STEP")
            AddButton({ viewModel.addInstruction(Place()) }, "PLACE")
            AddButton({ viewModel.addInstruction(Lift()) }, "LIFT")
            AddButton({ viewModel.addInstruction(LeftTurn()) }, "LEFTTURN")
            AddButton({ viewModel.addInstruction(RightTurn()) }, "RIGHTTURN")
            AddButton({ viewModel.addInstruction(End()) }, "END")
        }
    }
}

@Composable
fun AddButton(lambda: () -> Unit ,buttonName: String) {
    Button( onClick = lambda) {
        Text(text = buttonName)
    }
}