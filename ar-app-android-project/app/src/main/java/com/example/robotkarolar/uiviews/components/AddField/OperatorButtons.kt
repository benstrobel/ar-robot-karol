package com.example.robotkarolar.uiviews.components.AddField

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.uiviews.components.AddButton
import com.example.robotkarolar.uiviews.models.CodeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OperatorButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4)
    ) {
        item { AddButton(
            {
                val expr = EmptyExpression()
                val not = Not(expr)
                expr.parent = not
                viewModel.addInstruction(not)
            }, "NOT") }
        item { AddButton(lambda = {
            val left = EmptyExpression()
            val right = EmptyExpression()
            val and = And(left, right)
            left.parent = and
            right.parent = and
            viewModel.addInstruction(and)
        }, buttonName = "AND") }
        item { AddButton(lambda = {
            val left = EmptyExpression()
            val right = EmptyExpression()
            val or = Or(left, right)
            left.parent = or
            right.parent = or
            viewModel.addInstruction(or)
        }, buttonName = "OR") }
    }
}