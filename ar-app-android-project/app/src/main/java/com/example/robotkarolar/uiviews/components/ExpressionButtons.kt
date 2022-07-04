package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.expressions.IsBorder
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun ExpressionButtons(viewModel: CodeViewModel) {
    Column() {
        AddButton({viewModel.addInstruction(IsBorder())}, "ISBORDER")
    }
}
