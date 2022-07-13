package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.uiviews.models.CodeViewModel
import com.example.robotkarolar.uiviews.components.buttons.InstructionButton

@Composable
@ExperimentalMaterialApi
fun CodeRow(instruction: Instruction, viewModel: CodeViewModel) {
    Column {
        if(viewModel.repaintHelper.value){
            // Don't remove this, this allows to forcibly repaint the UI when changing repaintHelpers value
        }
        when(instruction) {
            is CodeBlock -> {
                instruction.instructions.forEach {
                    CodeRow(instruction = it, viewModel = viewModel)
                }
            }
            is If -> {
                createControllFlow(
                    instruction = instruction,
                    codeBlock = instruction.codeBlock,
                    viewModel = viewModel
                )
            }
            is While -> {
                createControllFlow(
                    instruction = instruction,
                    codeBlock = instruction.codeBlock,
                    viewModel = viewModel
                )
            }
            is Noop -> {}
            else -> {
                createSnipet(instruction = instruction, viewModel = viewModel)
            }
        }

        if(instruction == viewModel.cursor.value) {
            CodeCursor()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun createSnipet(instruction: Instruction, viewModel: CodeViewModel) {
    key(instruction.id) { DismissableCodeSnippet(instruction, viewModel)}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun createControllFlow(instruction: Instruction, codeBlock: CodeBlock, viewModel: CodeViewModel){
    createSnipet(instruction = instruction, viewModel = viewModel)
    Row {
        Spacer(modifier = Modifier.padding(15.dp))
        CodeRow(codeBlock, viewModel)
    }
}

@Composable
@ExperimentalMaterialApi
fun DismissableCodeSnippet(instruction: Instruction, viewModel: CodeViewModel) {
    val dismissState = rememberDismissState(
        initialValue = DismissValue.Default,
        confirmStateChange =  {
            if(it == DismissValue.DismissedToStart) {
                viewModel.removeInstruction(instruction)
            } else {
                false;
            }
        }
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.StartToEnd -> Color.Transparent
                DismissDirection.EndToStart -> Color.Red
                null -> Color.Transparent
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            )
        },
        dismissThresholds = {
            FractionalThreshold(0.1f)
        },
        dismissContent = {
            //CodeSnippet(instruction = instruction, viewModel.cursor)
            InstructionButton(instruction = instruction, viewModel = viewModel)
        }
    )
}

@Preview
@Composable
@ExperimentalMaterialApi
fun CodeRowPreview() {
    //Example Code for testing

    var controlFlow2 = If(And(Not(IsBorder()), IsBlock()), CodeBlock(mutableListOf(Step())))

    var controllFlow = While(IsBorder(), CodeBlock(mutableListOf(Step(), controlFlow2)))

    var examplecode = CodeBlock(mutableListOf(Step(), Place(), LeftTurn(), controllFlow, Step()))

    val model = CodeViewModel(examplecode)

    CodeRow(examplecode, model)
}