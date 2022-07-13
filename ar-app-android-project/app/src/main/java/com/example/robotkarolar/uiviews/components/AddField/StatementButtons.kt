package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.expressions.IsBorder
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.karollogic.instructions.visitors.NameRenderVisitor
import com.example.robotkarolar.uiviews.components.buttons.AddButton
import com.example.robotkarolar.uiviews.models.CodeViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun StatementButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        item { AddButton({ viewModel.addInstruction(Step()) }, NameRenderVisitor(Step()).get()) }
        item { AddButton({ viewModel.addInstruction(Place()) }, NameRenderVisitor(Place()).get()) }
        item { AddButton({ viewModel.addInstruction(Lift()) }, NameRenderVisitor(Lift()).get()) }
        item { AddButton({ viewModel.addInstruction(LeftTurn()) }, NameRenderVisitor(LeftTurn()).get()) }
        item { AddButton({ viewModel.addInstruction(RightTurn()) }, NameRenderVisitor(RightTurn()).get()) }
        item { AddButton({ viewModel.addInstruction(End()) }, NameRenderVisitor(End()).get()) }
    }
}