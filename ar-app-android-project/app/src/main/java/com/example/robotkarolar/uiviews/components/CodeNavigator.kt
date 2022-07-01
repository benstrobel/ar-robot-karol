package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.ArButton
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun CodeNavigator(viewModel: CodeViewModel) {
    Row {
        NextButton(viewModel = viewModel)
        BeforButton(viewModel = viewModel)
        //IndexButton(viewModel = viewModel)
        ClearButton(viewModel = viewModel)
    }
}

@Composable
fun IndexButton(viewModel: CodeViewModel) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.chain.value.updateIndex(-1)
        viewModel.chain.value.printAll()
    }) {
        Text(text = "Index")
    }
}

@Composable
fun NextButton(viewModel: CodeViewModel) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.next()
    }) {
        Text(text = "Next")
    }
}

@Composable
fun BeforButton(viewModel: CodeViewModel) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.before()
    }) {
        Text(text = "Before")
    }
}

@Composable
fun ClearButton(viewModel: CodeViewModel) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.clear()
        viewModel.chain.value.printAll()
    }) {
        Text(text = "Clear")
    }
}