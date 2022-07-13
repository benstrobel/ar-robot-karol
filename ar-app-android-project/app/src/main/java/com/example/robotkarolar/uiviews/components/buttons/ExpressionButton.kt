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
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.visitors.NameRenderVisitor
import com.example.robotkarolar.ui.theme.ExpressionText
import com.example.robotkarolar.ui.theme.Snipit1
import com.example.robotkarolar.ui.theme.Snipit2
import com.example.robotkarolar.uiviews.models.CodeViewModel
import com.example.robotkarolar.uiviews.components.CodeCursor

@Composable
fun ExpressionButton(expression: Expression, viewModel: CodeViewModel) {
    var color: MutableState<Color> = remember {
        when (expression) {
            is And, is Or, is Not -> mutableStateOf(Snipit2)
            else -> mutableStateOf(Snipit1)
        }
    } //TODO: PICK nicer colors

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(expression == viewModel.cursor.value) {
            CodeCursor()
        }

        Box(
            Modifier
                .padding(5.dp)
                .border(
                    width = 2.dp,
                    color = color.value,
                    shape = RoundedCornerShape(5.dp)
                )
                .clip(RoundedCornerShape(5.dp))
                .background(color.value.copy(alpha = if (expression == viewModel.cursor.value) 0.1f else 0.0f))
                .clickable(onClick = {
                    //TODO: Open change window
                    viewModel.cursor.value = expression
                })
                .padding(5.dp)
        ) {
            var textButton = NameRenderVisitor(expression).get()

            when (expression) {
                is And -> {
                    val and = expression as And

                    rowExpression(
                        textString = textButton,
                        expressionLeft = and.left,
                        expressionRight = and.right,
                        viewModel = viewModel
                    )
                }
                is Or -> {
                    val or = expression as Or

                    rowExpression(
                        textString = textButton,
                        expressionLeft = or.left,
                        expressionRight = or.right,
                        viewModel = viewModel
                    )
                }
                is Not -> {
                    val not = expression as Not

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        textExpression(textString = textButton)
                        ExpressionButton(expression = not.child, viewModel = viewModel)
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
        ExpressionButton(expression = expressionLeft, viewModel = viewModel)
        textExpression(textString = textString)
        ExpressionButton(expression = expressionRight, viewModel = viewModel)
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