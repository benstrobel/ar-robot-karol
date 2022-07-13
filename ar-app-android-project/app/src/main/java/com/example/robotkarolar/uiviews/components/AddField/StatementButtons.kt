package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.uiviews.components.buttons.AddButton
import com.example.robotkarolar.uiviews.models.CodeViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun StatementButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        item { AddButton({ viewModel.addInstruction(Step()) }, "STEP") }
        item { AddButton({ viewModel.addInstruction(Place()) }, "PLACE") }
        item { AddButton({ viewModel.addInstruction(Lift()) }, "LIFT") }
        item { AddButton({ viewModel.addInstruction(LeftTurn()) }, "LEFTTURN") }
        item { AddButton({ viewModel.addInstruction(RightTurn()) }, "RIGHTTURN") }
        item { AddButton({ viewModel.addInstruction(End()) }, "END") }
    }
}