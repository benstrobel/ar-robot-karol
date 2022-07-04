package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.robotkarolar.uiviews.CodeViewModel
import com.example.robotkarolar.uiviews.CodeViewModel2

@Composable
fun CodeNavigator(viewModel: CodeViewModel2) {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
        NextButton(viewModel = viewModel)
        BeforeButton(viewModel = viewModel)
        //IndexButton(viewModel = viewModel)
        ClearButton(viewModel = viewModel)
    }
}

@Composable
fun NextButton(viewModel: CodeViewModel2) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.next()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
        Text(text = "Next")
    }
}

@Composable
fun BeforeButton(viewModel: CodeViewModel2) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.previous()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
        Text(text = "Before")
    }
}

@Composable
fun ClearButton(viewModel: CodeViewModel2) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.clear()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
        Text(text = "Clear")
    }
}