package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.uiviews.models.CodeViewModel
import com.example.robotkarolar.uiviews.components.buttons.ArButton
import com.example.robotkarolar.uiviews.components.buttons.CodeNavButton


@Composable
fun CodeNavigatorBar(viewModel: CodeViewModel) {
    Row (
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        CodeNavButton(onClick = {viewModel.next()}, com.example.robotkarolar.R.drawable.down)
        CodeNavButton(onClick = {viewModel.previous()}, com.example.robotkarolar.R.drawable.up)
        Spacer(modifier = Modifier.weight(1.0f))
        ArButton(viewModel = viewModel)
    }
}

@Preview
@Composable
fun NavigationButtonPreview() {
    var viewModel = CodeViewModel()
    CodeNavigatorBar(viewModel = viewModel)
}