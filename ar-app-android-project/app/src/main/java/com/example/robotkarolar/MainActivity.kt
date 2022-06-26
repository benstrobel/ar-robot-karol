package com.example.robotkarolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionPrintVisitor
import com.example.robotkarolar.karollogic_ben.interpreter.Interpreter
import com.example.robotkarolar.karollogic_ben.interpreter.Parser
import com.example.robotkarolar.karollogic_ramona.Parts.CodeParts
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.Parts.ControllFlow
import com.example.robotkarolar.karollogic_ramona.conditions.BoolValue
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.enums.ControllFlowType
import com.example.robotkarolar.karollogic_ramona.enums.ExpressionTyp
import com.example.robotkarolar.karollogic_ramona.Parts.Chain
import com.example.robotkarolar.karollogic_ramona.karolWorld.World
import com.example.robotkarolar.ui.theme.RobotKarolArTheme
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RobotKarolArTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column {
                        RunButtonRamona()
                        RunButtonBen()
                    }
                }
            }
        }
    }
}

@Composable
fun RunButtonRamona() {
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
        Text(text = "Run ExampleCode (Ramona)")
    }
}

@Composable
fun RunButtonBen() {
    Button(onClick = {
        //Example Code for testing

        val testInput: String =
            "while not isborder:\n" +
            "    if not isblock:\n" +
            "        step\n" +
            "leftturn\n" +
            "leftturn\n" +
            "step\n" +
            "leftturn\n" +
            "leftturn\n" +
            "place\n" +
            "place\n" +
            "place"

        val instructions = Parser.parse(testInput.split("\n").toTypedArray())
        println("\n------------------------- Instructions: -------------------------\n")
        val printer = InstructionPrintVisitor()
        for (instruction in instructions) {
            instruction.accept(printer)
        }
        val world = com.example.robotkarolar.karollogic_ben.world.World()
        val robot = world.addEntity(0, 0)
        world.selectedEntity = robot
        println("\n------------------------- Execution: -------------------------\n")
        Interpreter.interpret(instructions, world)
    }) {
        Text(text = "Run ExampleCode (Ben)")
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RobotKarolArTheme {
        Column {
            RunButtonRamona()
            RunButtonBen()
        }
    }
}