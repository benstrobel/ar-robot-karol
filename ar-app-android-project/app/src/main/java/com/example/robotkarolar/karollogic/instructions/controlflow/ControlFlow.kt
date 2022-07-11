package com.example.robotkarolar.karollogic.instructions.controlflow

import com.example.robotkarolar.karollogic.instructions.Instruction

abstract class ControlFlow : Instruction() {
    abstract val codeBlock: CodeBlock
}