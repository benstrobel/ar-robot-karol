package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.visitors.NameRenderVisitor
import com.example.robotkarolar.uiviews.components.buttons.AddButton
import com.example.robotkarolar.uiviews.models.CodeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpressionButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        item {
            AddButton({ viewModel.addInstruction(IsBorder()) }, NameRenderVisitor(IsBorder()).get())
        }
        item {
            AddButton({viewModel.addInstruction(IsBlock())}, NameRenderVisitor(IsBlock()).get())
        }
        item {
            AddButton({viewModel.addInstruction(IsEast())}, NameRenderVisitor(IsEast()).get())
        }
        item {
            AddButton({viewModel.addInstruction(IsNorth())}, NameRenderVisitor(IsNorth()).get())
        }
        item {
            AddButton({viewModel.addInstruction(IsSouth())}, NameRenderVisitor(IsSouth()).get())
        }
        item {
            AddButton({viewModel.addInstruction(IsWest())}, NameRenderVisitor(IsWest()).get())
        }
        item {
            AddButton({viewModel.addInstruction(False())}, NameRenderVisitor(False()).get())
        }
        item {
            AddButton({viewModel.addInstruction(True())}, NameRenderVisitor(True()).get())
        }
    }
}
