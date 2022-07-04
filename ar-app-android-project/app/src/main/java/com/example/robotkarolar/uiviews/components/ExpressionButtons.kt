package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun ExpressionButtons(viewModel: CodeViewModel) {
    Column() {
        AddButton(
            {
                val expr = EmptyExpression()
                val not = Not(expr)
                expr.parent = not
                viewModel.addInstruction(not)
            }, "NOT")
        AddButton(lambda = {
            val left = EmptyExpression()
            val right = EmptyExpression()
            val and = And(left, right)
            left.parent = and
            right.parent = and
            viewModel.addInstruction(and)
        }, buttonName = "AND")
        AddButton(lambda = {
            val left = EmptyExpression()
            val right = EmptyExpression()
            val or = Or(left, right)
            left.parent = or
            right.parent = or
            viewModel.addInstruction(or)
        }, buttonName = "OR")
        AddButton({viewModel.addInstruction(IsBorder())}, "ISBORDER")
        AddButton({viewModel.addInstruction(IsBlock())}, "ISBLOCK")
        AddButton({viewModel.addInstruction(IsEast())}, "ISEAST")
        AddButton({viewModel.addInstruction(IsNorth())}, "ISNORTH")
        AddButton({viewModel.addInstruction(IsSouth())}, "ISSOUTH")
        AddButton({viewModel.addInstruction(IsWest())}, "ISWEST")
        AddButton({viewModel.addInstruction(False())}, "FALSE")
        AddButton({viewModel.addInstruction(True())}, "TRUE")
    }
}
