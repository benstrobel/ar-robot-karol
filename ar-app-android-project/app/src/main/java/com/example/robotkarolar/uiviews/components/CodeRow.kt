package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic_ramona.Parts.Chain
import com.example.robotkarolar.karollogic_ramona.Parts.CodeParts
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.Parts.ControllFlow
import com.example.robotkarolar.karollogic_ramona.conditions.BoolValue
import com.example.robotkarolar.karollogic_ramona.conditions.Conditions
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.enums.ConditionTyp
import com.example.robotkarolar.karollogic_ramona.enums.ControllFlowType
import com.example.robotkarolar.karollogic_ramona.enums.ExpressionTyp
import com.example.robotkarolar.uiviews.CodeCursorModel

@Composable
fun CodeRow(codeParts: CodeParts, codeCursorModel: CodeCursorModel) {
    Column {
        when(codeParts) {
            is Command -> CodeSnipit(codeParts = codeParts)
            is Chain -> {
                if(codeParts.code.size == 0 && codeCursorModel.cursorIndex == codeCursorModel.currentIndex) {
                    CodeCursor()
                }
                codeParts.code.forEach {
                    if (codeCursorModel.cursorIndex == codeCursorModel.currentIndex) {
                        CodeCursor()
                    }
                    CodeRow(codeParts = it, codeCursorModel)
                    codeCursorModel.pushCursor()
                }
                if (codeParts.code.size != 0 && codeCursorModel.cursorIndex == codeCursorModel.currentIndex) {
                    CodeCursor()
                }
            }
            is ControllFlow -> {
                when(codeParts.controllFlowType) {
                    ControllFlowType.IF -> {
                        CodeSnipit(codeParts = codeParts)
                        codeCursorModel.pushCursor()
                        Row {
                            Spacer(modifier = Modifier.padding(15.dp))
                            CodeRow(codeParts = codeParts.codeParts, codeCursorModel)
                        }
                    }
                    ControllFlowType.WHILE -> {
                        CodeSnipit(codeParts = codeParts)
                        codeCursorModel.pushCursor()
                        Row {
                            Spacer(modifier = Modifier.padding(15.dp))
                            CodeRow(codeParts = codeParts.codeParts, codeCursorModel)
                        }
                    }
                }
            }
            else -> "Error"
        }
    }
}

@Composable
fun CodeSnipit(codeParts: CodeParts) {
    Box(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(5.dp))
        .background(codeParts.returnColor())
        .padding(5.dp)
    ) {
        Text(text = codeParts.returnTextValue())
    }
}

@Preview
@Composable
fun SnipitPreview() {
    CodeSnipit(codeParts = Command(CommandType.TURN))
}

@Preview
@Composable
fun CodeRowPreview() {
    //Example Code for testing
    var command1: Command = Command(CommandType.STEP)
    var command2: Command = Command(CommandType.PLACEWATER)
    var command4: Command = Command(CommandType.TURNLEFT)

    var command3: Command = Command(CommandType.STEP)
    var chaine: Chain = Chain(mutableListOf(command3))

    var condition: Conditions = Conditions(
        BoolValue(ExpressionTyp.NOTISBOARDER),
        BoolValue(ExpressionTyp.ISBLOCK), ConditionTyp.AND)
    var controllFlow2: ControllFlow = ControllFlow(ControllFlowType.IF, condition, chaine)

    var chain2: Chain = Chain(mutableListOf(command3, controllFlow2))
    var controllFlow: ControllFlow = ControllFlow(ControllFlowType.WHILE, BoolValue(ExpressionTyp.ISBOARDER), chain2)

    var examplecode: MutableList<CodeParts> = mutableListOf(command1, command2, command4, controllFlow, command1)
    val karol: Chain = Chain(examplecode)

    CodeRow(codeParts = karol, CodeCursorModel(10))
    //CodeRow(codeParts = Command(CommandType.STEP))
}