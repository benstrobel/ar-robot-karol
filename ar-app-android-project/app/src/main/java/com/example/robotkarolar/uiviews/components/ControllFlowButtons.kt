package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.uiviews.CodeViewModel

private fun newCodeBlock(): CodeBlock {
    val noop = Noop()
    return CodeBlock(mutableListOf(noop))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ControllFlowButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4)
    ) {
        item { ControllFlowButton(
            {
                val block = newCodeBlock()
                val expr = EmptyExpression()
                val cf = If(expr, block)
                expr.parent = cf
                viewModel.addInstruction(cf)
                block.parent = cf
            }, "IF") }
        item { ControllFlowButton(
            {
                val block = newCodeBlock()
                val expr = EmptyExpression()
                val cf = While(expr, block)
                expr.parent = cf
                viewModel.addInstruction(cf)
                block.parent = cf
            }, "WHILE") }
    }
}

@Preview
@Composable
fun ControllFlowButtonPrev() {
    var viewModel = CodeViewModel()
    ControllFlowButtons(viewModel = viewModel)
}

@Composable
fun ControllFlowButton(lambda: () -> Unit, buttonName: String) {
    Button( onClick = lambda) {
        Text(text = buttonName)
    }
}