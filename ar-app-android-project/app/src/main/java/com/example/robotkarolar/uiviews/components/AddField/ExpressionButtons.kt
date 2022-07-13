package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.uiviews.models.CodeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpressionButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4)
    ) {
        item { AddButton({viewModel.addInstruction(IsBorder())}, "ISBORDER") }
        item { AddButton({viewModel.addInstruction(IsBlock())}, "ISBLOCK") }
        item { AddButton({viewModel.addInstruction(IsEast())}, "ISEAST") }
        item { AddButton({viewModel.addInstruction(IsNorth())}, "ISNORTH") }
        item { AddButton({viewModel.addInstruction(IsSouth())}, "ISSOUTH") }
        item { AddButton({viewModel.addInstruction(IsWest())}, "ISWEST") }
        item { AddButton({viewModel.addInstruction(False())}, "FALSE") }
        item { AddButton({viewModel.addInstruction(True())}, "TRUE") }
    }
}
