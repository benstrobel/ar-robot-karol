package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn
import com.example.robotkarolar.karollogic.instructions.statements.Place
import com.example.robotkarolar.karollogic.instructions.statements.Step
import com.example.robotkarolar.uiviews.CodeViewModel
import com.example.robotkarolar.uiviews.HomeView
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