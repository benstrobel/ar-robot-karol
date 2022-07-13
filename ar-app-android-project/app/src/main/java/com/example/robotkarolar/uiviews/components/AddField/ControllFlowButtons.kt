package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.karollogic.instructions.visitors.NameRenderVisitor
import com.example.robotkarolar.uiviews.components.buttons.AddButton
import com.example.robotkarolar.uiviews.models.CodeViewModel

private fun newCodeBlock(): CodeBlock {
    val noop = Noop()
    return CodeBlock(mutableListOf(noop))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ControllFlowButtons(viewModel: CodeViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        item { AddButton({
                val block = newCodeBlock()
                val expr = EmptyExpression()
                val cf = If(expr, block)
                expr.parent = cf
                block.parent = cf

                viewModel.addInstruction(cf)
            }, NameRenderVisitor(If(EmptyExpression(), newCodeBlock())).get())
        }
        item { AddButton({
                val block = newCodeBlock()
                val expr = EmptyExpression()
                val cf = While(expr, block)
                expr.parent = cf
                block.parent = cf

                viewModel.addInstruction(cf)
            }, NameRenderVisitor(While(EmptyExpression(), newCodeBlock())).get())
        }
    }
}

@Preview
@Composable
fun ControllFlowButtonPrev() {
    var viewModel = CodeViewModel()
    ControllFlowButtons(viewModel = viewModel)
}