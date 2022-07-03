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
import com.example.robotkarolar.karollogic_ramona.Parts.Chain
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.Parts.ControllFlow
import com.example.robotkarolar.karollogic_ramona.conditions.BoolValue
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.enums.ControllFlowType
import com.example.robotkarolar.karollogic_ramona.enums.ExpressionTyp
import com.example.robotkarolar.karollogic_ramona.enums.ExpressionTypHandler
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun ControllFlowButtons(viewModel: CodeViewModel) {
    Column() {
        ControllFlowButton(viewModel = viewModel, controllFlowTyp = ControllFlowType.IF)
        ControllFlowButton(viewModel = viewModel, controllFlowTyp = ControllFlowType.WHILE)
    }
}

@Preview
@Composable
fun ControllFlowButtonPrev() {
    var viewModel = CodeViewModel()
    ControllFlowButtons(viewModel = viewModel)
}

@Composable
fun ControllFlowButton(viewModel: CodeViewModel, controllFlowTyp: ControllFlowType) {

    var selectedItem = remember {mutableStateOf(ExpressionTyp.ISBLOCK)}

    Button(modifier = Modifier
        .padding(5.dp) ,
        onClick = {
        when(controllFlowTyp) {
            ControllFlowType.WHILE -> viewModel.addToCode(ControllFlow(ControllFlowType.WHILE, BoolValue(
                selectedItem.value), Chain(mutableListOf()))) //condition should be pickable
            ControllFlowType.IF -> viewModel.addToCode(ControllFlow(ControllFlowType.IF, BoolValue(
                selectedItem.value), Chain(mutableListOf()))) //condition should be pickable
        }

            //Testing TODO:REMOVE
            viewModel.chain.value.printAll()
    }) {
        Row() {
            Text(text = (controllFlowTyp.toString()))

            //Dropdown
            val listEnums = enumValues<ExpressionTyp>().toList()
            val list = listEnums.map { ExpressionTypHandler().toString(it) }
            dropDownMenu(list,selectedItem)
        }
    }
}

@Composable
fun dropDownMenu(list: List<String>, selectedItem2: MutableState<ExpressionTyp>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {
                selectedItem = it
                selectedItem2.value = ExpressionTypHandler().fromString(selectedItem)
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
            list.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedItem = label
                    selectedItem2.value = ExpressionTypHandler().fromString(selectedItem)
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}