package com.example.robotkarolar.uiviews

class CodeCursorModel(val cursorIndex: Int) {
    var currentIndex: Int = 0
        private set

    fun pushCursor() {
        currentIndex += 1
    }
}