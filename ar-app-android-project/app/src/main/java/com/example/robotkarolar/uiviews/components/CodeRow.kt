package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic_ben.instructions.Instruction
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.If
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.While
import com.example.robotkarolar.karollogic_ben.instructions.expressions.And
import com.example.robotkarolar.karollogic_ben.instructions.expressions.IsBlock
import com.example.robotkarolar.karollogic_ben.instructions.expressions.IsBorder
import com.example.robotkarolar.karollogic_ben.instructions.expressions.Not
import com.example.robotkarolar.karollogic_ben.instructions.statements.*

@Composable
@ExperimentalMaterialApi
fun CodeRow(codeBlock: Instruction, cursor: MutableState<Instruction>) {
    Column {
        when(codeBlock) {
            is CodeBlock -> {
                (codeBlock as CodeBlock).instructions.forEach {
                    CodeRow(codeBlock = it, cursor = cursor)
                    if(codeBlock == cursor.value) {
                        CodeCursor()
                    }
                }
            }
            is If -> {
                DismissableCodeSnippet(instruction = codeBlock, cursor)
                Row {
                    Spacer(modifier = Modifier.padding(15.dp))
                    CodeRow(codeBlock = codeBlock.codeBlock, cursor = cursor)
                }
            }
            is While -> {
                DismissableCodeSnippet(instruction = codeBlock, cursor)
                Row {
                    Spacer(modifier = Modifier.padding(15.dp))
                    CodeRow(codeBlock = codeBlock.codeBlock, cursor = cursor)
                }
            }
            is End, is LeftTurn, is Lift, is Place, is RightTurn, is Step -> {
                DismissableCodeSnippet(instruction = codeBlock, cursor = cursor)
            }
        }
        if(codeBlock == cursor.value) {
            CodeCursor()
        }
    }
}

@Composable
fun CodeSnippet(instruction: Instruction) {
    Box(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(5.dp))
        .background(MaterialTheme.colors.primary)
        .padding(5.dp)
    ) {
        Text(text = instruction::class.java.simpleName.uppercase())
    }
}

@Composable
@ExperimentalMaterialApi
fun DismissableCodeSnippet(instruction: Instruction, cursor: MutableState<Instruction>) {
    val dismissState = rememberDismissState(initialValue = DismissValue.Default, confirmStateChange =  {
        if(instruction.parent != null) {
            when(cursor.value.parent) {
                is CodeBlock -> cursor.value = (instruction.parent as CodeBlock).getPreviousBefore(instruction)
                is While -> cursor.value = (instruction.parent as While).codeBlock.getPreviousBefore(instruction)
                is If -> cursor.value = (instruction.parent as If).codeBlock.getPreviousBefore(instruction)
            }
        }
        instruction.delete()
    })

    SwipeToDismiss(state = dismissState, directions = setOf(DismissDirection.EndToStart), background = {
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
    }, dismissContent = { CodeSnippet(instruction = instruction)})
}


@Preview
@Composable
fun SnippetPreview() {
    CodeSnippet(instruction = LeftTurn())
}

@Preview
@Composable
@ExperimentalMaterialApi
fun CodeRowPreview() {
    //Example Code for testing

    var controlFlow2 = If(And(Not(IsBorder()), IsBlock()), CodeBlock(arrayOf(Step())))

    var controllFlow = While(IsBorder(), CodeBlock(arrayOf(Step(), controlFlow2)))

    var examplecode = CodeBlock(arrayOf(Step(), Place(), LeftTurn(), controllFlow, Step()))

    CodeRow(examplecode, remember { mutableStateOf(controllFlow) })
}