package com.example.robotkarolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.robotkarolar.karollogic.Parts.CodeParts
import com.example.robotkarolar.karollogic.Parts.Command
import com.example.robotkarolar.karollogic.Parts.ControllFlow
import com.example.robotkarolar.karollogic.conditions.BoolValue
import com.example.robotkarolar.karollogic.enums.CommandType
import com.example.robotkarolar.karollogic.enums.ControllFlowType
import com.example.robotkarolar.karollogic.enums.ExpressionTyp
import com.example.robotkarolar.karollogic.Parts.Chain
import com.example.robotkarolar.karollogic.karolWorld.World
import com.example.robotkarolar.ui.theme.RobotKarolArTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RobotKarolArTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    RunButton()
                }
            }
        }
    }
}

@Composable
fun RunButton() {
    Button(onClick = {
        //Example Code for testing
        var command1: Command = Command(CommandType.STEP)
        var command2: Command = Command(CommandType.PLACEWATER)
        var command4: Command = Command(CommandType.TURNLEFT)

        var command3: Command = Command(CommandType.STEP)
        var chaine: Chain = Chain(mutableListOf(command3))
        var controllFlow: ControllFlow = ControllFlow(ControllFlowType.WHILE, BoolValue(ExpressionTyp.ISBOARDER, true), chaine)


        var examplecode: MutableList<CodeParts> = mutableListOf(command1, command2, command4, controllFlow)

        val karol: Chain = Chain(examplecode)

        karol.returnCommands(World())
    }) {
        Text(text = "Run ExampleCode")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RobotKarolArTheme {
        RunButton()
    }
}