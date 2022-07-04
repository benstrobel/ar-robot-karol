package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.ui.unit.sp
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun CodeNavigator(viewModel: CodeViewModel) {
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Spacer(modifier = Modifier.weight(1f))
        BeforeButton(viewModel = viewModel)
        Spacer(modifier = Modifier.weight(0.4f))
        NextButton(viewModel = viewModel)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun NextButton(viewModel: CodeViewModel) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.next()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
        Icon(Icons.Filled.ArrowDownward, "next")
    }
}

@Composable
fun BeforeButton(viewModel: CodeViewModel) {
    Button(onClick = {
        //Example Code for Testing
        viewModel.previous()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)) {
        Icon(Icons.Filled.ArrowUpward, "next")
    }
}