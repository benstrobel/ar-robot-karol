package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic_ramona.Parts.Chain
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.Parts.ControllFlow
import com.example.robotkarolar.karollogic_ramona.conditions.BoolValue
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.enums.ControllFlowType
import com.example.robotkarolar.karollogic_ramona.enums.ExpressionTyp
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun ControllFlowButtons(viewModel: CodeViewModel) {
    Column() {
        ControllFlowButton(viewModel = viewModel, ControllFlowType.WHILE)
        ControllFlowButton(viewModel = viewModel, ControllFlowType.IF)
    }
}

@Composable
fun ControllFlowButton (viewModel: CodeViewModel, controllFlowType: ControllFlowType) {
    Button(onClick = {
        when(controllFlowType) {
            ControllFlowType.WHILE -> viewModel.addToCode(ControllFlow(ControllFlowType.WHILE, BoolValue(
                ExpressionTyp.ISBOARDER, true), Chain(mutableListOf()))) //condition should be pickable
            ControllFlowType.IF -> viewModel.addToCode(ControllFlow(ControllFlowType.IF, BoolValue(
                ExpressionTyp.ISBOARDER, true), Chain(mutableListOf()))) //condition should be pickable
        }
    }) {
        Text(text = (controllFlowType.toString()))
    }
}