package com.example.robotkarolar.uiviews.components.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression
import com.example.robotkarolar.uiviews.models.AddFieldStates
import com.example.robotkarolar.uiviews.models.CodeViewModel

@Composable
fun AddOptionsBar(viewModel: CodeViewModel) {
    Row (
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        if (viewModel.cursor.value is EmptyExpression) {
            if(viewModel.addFieldState.value == AddFieldStates.Statements || viewModel.addFieldState.value == AddFieldStates.ControllFlow) {
                viewModel.addFieldState.value = AddFieldStates.Expressions
            }

            AddOptionItem(
                isSelected = (viewModel.addFieldState.value == AddFieldStates.Expressions),
                onClick = { viewModel.addFieldState.value = AddFieldStates.Expressions},
                text = "Expression"
            )

            AddOptionItem(
                isSelected = (viewModel.addFieldState.value == AddFieldStates.Operator),
                onClick = { viewModel.addFieldState.value = AddFieldStates.Operator},
                text = "Operator"
            )
        } else {
            if(viewModel.addFieldState.value == AddFieldStates.Expressions || viewModel.addFieldState.value == AddFieldStates.Operator) {
                viewModel.addFieldState.value = AddFieldStates.Statements
            }

            AddOptionItem(
                isSelected = (viewModel.addFieldState.value == AddFieldStates.Statements),
                onClick = { viewModel.addFieldState.value = AddFieldStates.Statements},
                text = "Statement"
            )

            AddOptionItem(
                isSelected = (viewModel.addFieldState.value == AddFieldStates.ControllFlow),
                onClick = { viewModel.addFieldState.value = AddFieldStates.ControllFlow},
                text = "ControllFlow"
            )
        }
    }
}

@Composable
fun AddOptionItem( isSelected: Boolean, onClick: () -> Unit, text: String) {
    val background = if (isSelected) MaterialTheme.colors.primaryVariant.copy(alpha = 0.1f) else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = text, color = contentColor)
        }
    }
}

@Preview
@Composable
fun PrevItem() {
    AddOptionItem(isSelected = true, onClick = {}, text = "Statement")
}

@Preview
@Composable
fun AddOptionsBarPreview() {
    var viewModel = CodeViewModel()
    AddOptionsBar(viewModel = viewModel)
}