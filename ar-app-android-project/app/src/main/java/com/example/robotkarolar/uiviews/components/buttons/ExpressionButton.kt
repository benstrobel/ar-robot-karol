package com.example.robotkarolar.uiviews.components.buttons

import androidx.compose.foundation.background
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
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.ui.theme.CursorColor
import com.example.robotkarolar.ui.theme.ExpressionText
import com.example.robotkarolar.ui.theme.Snipit1
import com.example.robotkarolar.ui.theme.Snipit2
import com.example.robotkarolar.uiviews.CodeViewModel
import com.example.robotkarolar.uiviews.components.CodeCursor

@Composable
fun ExpressionButton(expression: Expression, viewModel: CodeViewModel) {
    var expressionOfButton: MutableState<Expression> = remember {
        mutableStateOf(expression)
    }

    var color: MutableState<Color> = remember {
        when (expressionOfButton.value) {
            is And, is Or, is Not -> mutableStateOf(Snipit2)
            else -> mutableStateOf(Snipit1)
        }
    } //TODO: PICK nicer colors

    Box(
        Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color.value)
            .clickable(onClick = {
                //TODO: Open change window
                viewModel.cursor.value = expressionOfButton.value
            })
            .padding(5.dp)
    ) {
        var textButton = when(expressionOfButton.value) {
            is EmptyExpression -> "Pick"
            is False -> "false"
            is IsBlock -> "isBlock"
            is IsBorder -> "isBorder"
            is IsEast -> "isEast"
            is IsNorth -> "isNorth"
            is IsSouth -> "isSouth"
            is IsWest -> "isWest"
            is True -> "true"
            is And -> "AND"
            is Not -> "NOT"
            is Or -> "OR"
            else -> ""
        }

        Row {
            if(expression == viewModel.cursor.value) {
                CodeCursor()
            }

            when (expressionOfButton.value) {
                is And -> {
                    val and = expressionOfButton.value as And

                    rowExpression(
                        textString = textButton,
                        expressionLeft = and.left,
                        expressionRight = and.right,
                        viewModel = viewModel
                    )
                }
                is Or -> {
                    val or = expressionOfButton.value as Or

                    rowExpression(
                        textString = textButton,
                        expressionLeft = or.left,
                        expressionRight = or.right,
                        viewModel = viewModel
                    )
                }
                is Not -> {
                    val not = expressionOfButton.value as Not

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        textExpression(textString = "(")
                        textExpression(textString = textButton)
                        ExpressionButton(expression = not.child, viewModel = viewModel)
                        textExpression(textString = ")")
                    }
                }
                else -> {
                    textExpression(textButton)
                }
            }
        }
    }
}

@Composable
fun rowExpression(textString: String, expressionLeft: Expression, expressionRight: Expression, viewModel: CodeViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        textExpression(textString = "(")
        ExpressionButton(expression = expressionLeft, viewModel = viewModel)
        textExpression(textString = textString)
        ExpressionButton(expression = expressionRight, viewModel = viewModel)
        textExpression(textString = ")")
    }
}

@Composable
fun textExpression(textString: String) {
    Text(
        text = textString,
        style = MaterialTheme.typography.subtitle1,
        color = ExpressionText,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun ExpressionButtonPreview() {
    var viewModel = CodeViewModel()

    Column() {
        ExpressionButton(expression = IsWest(), viewModel = viewModel)
        ExpressionButton(And(EmptyExpression(), EmptyExpression()), viewModel = viewModel)
        ExpressionButton(expression = Not(True()), viewModel = viewModel)
    }
}