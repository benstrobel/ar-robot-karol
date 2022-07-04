package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.Noop
import com.example.robotkarolar.uiviews.CodeViewModel

val expressionMap = mapOf(
    "TRUE" to { True() },
    "FALSE" to { False() },
    "ISBLOCK" to { IsBlock() },
    "ISBORDER" to { IsBorder() },
    "ISEAST" to { IsEast() },
    "ISNORTH" to { IsNorth() },
    "ISSOUTH" to { IsSouth() },
    "ISWEST" to { IsWest() }
)

private fun newCodeBlock(): CodeBlock {
    val noop = Noop()
    val block = CodeBlock()
    block.addInstruction(noop)
    return block
}

@Composable
fun ControllFlowButtons(viewModel: CodeViewModel) {
    Column() {
        ControllFlowButton({expr -> val block = newCodeBlock(); val cf = If(expr, block); viewModel.addInstruction(cf); block.parent = cf}, "IF")
        ControllFlowButton({expr -> val block = newCodeBlock(); val cf = While(expr, block); viewModel.addInstruction(cf); block.parent = cf}, "WHILE")
    }
}

@Preview
@Composable
fun ControllFlowButtonPrev() {
    var viewModel = CodeViewModel()
    ControllFlowButtons(viewModel = viewModel)
}

@Composable
fun ControllFlowButton(lambda: (Expression) -> Unit, buttonName: String) {

    var selectedExpression: MutableState<Expression> = remember { mutableStateOf(IsBorder())}

    Button(modifier = Modifier
        .padding(5.dp) ,
        onClick = {lambda.invoke(selectedExpression.value)}) {
        Row() {
            Text(text = (buttonName))

            //Dropdown
            dropDownMenu(selectedExpression)
        }
    }
}

@Composable
fun dropDownMenu(selectedExpression: MutableState<Expression>) {
    var expanded by remember { mutableStateOf(false) }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedExpression.value::class.java.simpleName.uppercase(),
            onValueChange = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(text = "Selected Expression") },
            trailingIcon = {
                Icon(icon, contentDescription = "", Modifier.clickable{ expanded = !expanded} )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current){ textFieldSize.width.toDp() })
        ) {
            expressionMap.toList().forEach { value ->
                DropdownMenuItem(onClick = {
                    selectedExpression.value = value.second.invoke()
                    expanded = false
                }) {
                    Text(text = value.first)
                }

            }
        }
    }
}