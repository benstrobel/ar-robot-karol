package com.example.robotkarolar.uiviews.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.karollogic.instructions.visitors.NameRenderVisitor
import com.example.robotkarolar.ui.theme.InstructionText
import com.example.robotkarolar.ui.theme.Snipit3
import com.example.robotkarolar.uiviews.models.AddFieldStates
import com.example.robotkarolar.uiviews.models.CodeViewModel

@Composable
fun InstructionButton(instruction: Instruction, viewModel: CodeViewModel) {
    var instructionOfButton: MutableState<Instruction> = remember {
        mutableStateOf(instruction)
    }

    var color: MutableState<Color> = remember {
        when (instructionOfButton.value) {
            else -> mutableStateOf(Snipit3)
        }
    } //TODO: PICK nicer and diffrent colors

    Box(
        Modifier
            .padding(5.dp)
            .border(
                width = 2.dp,
                color = color.value,
                shape = RoundedCornerShape(5.dp)
            )
            .clip(RoundedCornerShape(5.dp))
            .background(color.value.copy(alpha = if (instruction == viewModel.cursor.value) 0.1f else 0.0f))
            .clickable(onClick = {
                //TODO: Open change window and set curser
                viewModel.cursor.value = instructionOfButton.value

                viewModel.addFieldState.value = AddFieldStates.Statements
            })
            .padding(5.dp)
    ) {
        var textButton = NameRenderVisitor(instruction).get()

        when (instructionOfButton.value) {
            is While -> {
                rowInstruction(
                    textString = textButton,
                    expression = (instructionOfButton.value as While).condition,
                    viewModel = viewModel
                )
            }
            is If -> {
                rowInstruction(
                    textString = textButton,
                    expression = (instructionOfButton.value as If).condition,
                    viewModel = viewModel
                )
            }
            else -> {
                textInstruction(textButton)
            }
        }
    }
}

@Composable
fun rowInstruction(textString: String, expression: Expression, viewModel: CodeViewModel) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        textInstruction(textString = textString)
        Spacer(modifier = Modifier.padding(2.dp))
        ExpressionButton(expression = expression, viewModel = viewModel)
    }
}

@Composable
fun textInstruction(textString: String) {
    Text(
        text = textString,
        style = MaterialTheme.typography.subtitle1,
        color = InstructionText,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
fun InstructionButtonPreview() {
    var viewModel = CodeViewModel()

    Column() {
        InstructionButton(instruction = Place(), viewModel = viewModel)
        InstructionButton(instruction = If(EmptyExpression(), CodeBlock(mutableListOf())), viewModel = viewModel)
    }
}