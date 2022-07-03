package com.example.robotkarolar.uiviews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CodeCursor() {
    Box(modifier = Modifier
        .width(10.dp)
        .height(7.dp)
        .fillMaxWidth(0.03f)
        .fillMaxHeight(0.025f)
        .clip(AbsoluteCutCornerShape(topRightPercent = 50, bottomRightPercent = 50))
        .background(MaterialTheme.colors.secondary)
    )
}

@Preview
@Composable
fun CodeCursorPreview() {
    CodeCursor()
}