package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.Noop
import com.example.robotkarolar.uiviews.CodeViewModel

private fun newCodeBlock(): CodeBlock {
    val noop = Noop()
    val block = CodeBlock()
    block.addInstruction(noop)
    return block
}

@Composable
fun ControllFlowButtons(viewModel: CodeViewModel) {
    Column() {
        ControllFlowButton(
            {
                val block = newCodeBlock()
                val expr = EmptyExpression()
                val cf = If(expr, block)
                expr.parent = cf
                viewModel.addInstruction(cf)
                block.parent = cf
           }, "IF")
        ControllFlowButton(
            {
                val block = newCodeBlock()
                val expr = EmptyExpression()
                val cf = While(expr, block)
                expr.parent = cf
                viewModel.addInstruction(cf)
                block.parent = cf
            }, "WHILE")
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