package com.example.robotkarolar.uiviews

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.robotkarolar.karollogic_ramona.Parts.Chain
import com.example.robotkarolar.karollogic_ramona.Parts.CodeParts
import com.example.robotkarolar.karollogic_ramona.Parts.Command
import com.example.robotkarolar.karollogic_ramona.enums.CommandType


class CodeViewModel: ViewModel() {
    //States
    val currentIndex = mutableStateOf<Int>(0)
    var chain =  mutableStateOf<Chain>(Chain(mutableListOf()))
    val size = mutableStateOf<Int>(0)

    fun addToCode(codeParts: CodeParts) {
        chain.value.insertAt(currentIndex.value, codeParts)
        size.value = chain.value.size()
        currentIndex.value += 1

        //Reload State
        val cur = chain.value.code
        chain.value = Chain(cur)

        println("CurrentIndex: " + currentIndex.value)
        println("Size: " + size.value)
    }

    fun next() {
        if(currentIndex.value < size.value) {
            currentIndex.value += 1
            println("CurrentIndex: " + currentIndex.value)
        }
    }

    fun before() {
        if(currentIndex.value > 0){
            currentIndex.value -= 1
            println("CurrentIndex: " + currentIndex.value)
        }
    }

    fun clear() {
        chain.value = Chain(mutableListOf())
        currentIndex.value = 0
        size.value = 0
    }
}