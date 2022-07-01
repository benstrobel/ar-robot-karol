package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.uiviews.CodeViewModel

@Composable
fun CommandButtons(viewModel: CodeViewModel) {
    Column() {
        AddButton(viewModel = viewModel, commandTyp = CommandType.STEP)
        AddButton(viewModel = viewModel, commandTyp = CommandType.PLACEGRAS)
        AddButton(viewModel = viewModel, commandTyp = CommandType.PLACESTONE)
        AddButton(viewModel = viewModel, commandTyp = CommandType.PLACEWATER)
        AddButton(viewModel = viewModel, commandTyp = CommandType.REMOVE)
        AddButton(viewModel = viewModel, commandTyp = CommandType.TURN)
        AddButton(viewModel = viewModel, commandTyp = CommandType.TURNLEFT)
        AddButton(viewModel = viewModel, commandTyp = CommandType.TURNRIGHT)
    }
}

@Composable
fun AddButton(viewModel: CodeViewModel, commandTyp: CommandType) {
    Button(onClick = {
        when(commandTyp) {
            CommandType.REMOVE -> viewModel.addToCode(Command(CommandType.REMOVE))
            CommandType.TURN -> viewModel.addToCode(Command(CommandType.TURN))
            CommandType.TURNRIGHT -> viewModel.addToCode(Command(CommandType.TURNRIGHT))
            CommandType.TURNLEFT -> viewModel.addToCode(Command(CommandType.TURNLEFT))
            CommandType.STEP -> viewModel.addToCode(Command(CommandType.STEP))
            CommandType.PLACEWATER -> viewModel.addToCode(Command(CommandType.PLACEWATER))
            CommandType.PLACESTONE -> viewModel.addToCode(Command(CommandType.PLACESTONE))
            CommandType.PLACEGRAS -> viewModel.addToCode(Command(CommandType.PLACEGRAS))
            else -> "Error while adding"
        }

        viewModel.chain.value.printAll() //TODO: DELETE
    }) {
        Text(text = (commandTyp.toString()))
    }
}