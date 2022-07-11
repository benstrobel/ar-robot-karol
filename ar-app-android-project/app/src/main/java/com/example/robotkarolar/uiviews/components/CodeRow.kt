package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.karollogic.instructions.visitors.NameRenderVisitor
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
@ExperimentalMaterialApi
fun CodeRow(codeBlock: Instruction, viewModel: CodeViewModel) {
    Column {
        when(codeBlock) {
            is CodeBlock -> {
                (codeBlock as CodeBlock).instructions.forEach {
                    CodeRow(it, viewModel)
                    if(codeBlock == viewModel.cursor.value) {
                        CodeCursor()
                    }
                }
            }
            is If -> {
                key(codeBlock.id) { DismissableCodeSnippet(codeBlock, viewModel) }
                Row {
                    Spacer(modifier = Modifier.padding(15.dp))
                    CodeRow(codeBlock.codeBlock, viewModel)
                }
            }
            is While -> {
                key(codeBlock.id) { DismissableCodeSnippet(codeBlock, viewModel) }
                Row {
                    Spacer(modifier = Modifier.padding(15.dp))
                    CodeRow(codeBlock.codeBlock, viewModel)
                }
            }
            is End, is LeftTurn, is Lift, is Place, is RightTurn, is Step -> {
                key(codeBlock.id) { DismissableCodeSnippet(codeBlock, viewModel) }
            }
        }
        if(codeBlock == viewModel.cursor.value) {
            CodeCursor()
        }
    }
}

@Composable
fun CodeSnippet(instruction: Instruction, cursor: MutableState<Instruction>, expression: Boolean = false) {

    var modifier = if (expression)
        Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colors.primary)
    else
        Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colors.primary)
            .padding(5.dp)

    Box(modifier = modifier) {
        Row(){
            when(instruction) {
                is If -> {
                    Text(text = NameRenderVisitor(
                        instruction
                    ).get())
                    Text(text= " ( ")
                    if(instruction.condition == cursor.value) {
                        CodeCursor()
                    }
                    CodeSnippet(instruction = instruction.condition, cursor, true)
                    Text(text= " ) ")
                }
                is While -> {
                    Text(text = NameRenderVisitor(
                        instruction
                    ).get())
                    Text(text= " ( ")
                    if(instruction.condition == cursor.value) {
                        CodeCursor()
                    }
                    CodeSnippet(instruction = instruction.condition, cursor, true)
                    Text(text= " ) ")
                }
                is And -> {
                    Text(text= " ( ")
                    if(instruction.left == cursor.value) {
                        CodeCursor()
                    }
                    CodeSnippet(instruction = instruction.left, cursor, true)
                    Text(text= " ")
                    Text(text = NameRenderVisitor(
                        instruction
                    ).get())
                    Text(text= " ")
                    if(instruction.right == cursor.value) {
                        CodeCursor()
                    }
                    CodeSnippet(instruction = instruction.right, cursor, true)
                    Text(text= " ) ")
                }
                is Or -> {
                    Text(text= " ( ")
                    if(instruction.left == cursor.value) {
                        CodeCursor()
                    }
                    CodeSnippet(instruction = instruction.left, cursor, true)
                    Text(text= " ")
                    Text(text = NameRenderVisitor(
                        instruction
                    ).get())
                    Text(text= " ")
                    if(instruction.right == cursor.value) {
                        CodeCursor()
                    }
                    CodeSnippet(instruction = instruction.right, cursor, true)
                    Text(text= " ) ")
                }
                is Not -> {
                    Text(text = NameRenderVisitor(
                        instruction
                    ).get())
                    Text(text= " ( ")
                    Text(text= " ")
                    if(instruction.child == cursor.value) {
                        CodeCursor()
                    }
                    CodeSnippet(instruction = instruction.child, cursor, true)
                    Text(text= " ")
                    Text(text= " ) ")
                }
                else -> {
                    Text(text = NameRenderVisitor(
                        instruction
                    ).get())
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
fun DismissableCodeSnippet(instruction: Instruction, viewModel: CodeViewModel) {
    val dismissState = rememberDismissState(initialValue = DismissValue.Default, confirmStateChange =  {
        if(instruction.parent != null) {
            when(viewModel.cursor.value.parent) {
                is CodeBlock -> viewModel.cursor.value = viewModel.getPreviousBefore((instruction.parent as CodeBlock), instruction)!!
                is While -> viewModel.cursor.value = viewModel.getPreviousBefore((instruction.parent as While).codeBlock, instruction)!!
                is If -> viewModel.cursor.value = viewModel.getPreviousBefore((instruction.parent as If).codeBlock, instruction)!!
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
    }, dismissContent = { CodeSnippet(instruction = instruction, viewModel.cursor)})
}


@Preview
@Composable
fun SnippetPreview() {
    val instruction = LeftTurn()
    CodeSnippet(instruction = instruction, remember { mutableStateOf(instruction) })
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