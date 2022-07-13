package com.example.robotkarolar.uiviews.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddButton(onClick: () -> Unit, text: String) {
    val background = MaterialTheme.colors.primaryVariant.copy(alpha = 0.1f)
    val contentColor = MaterialTheme.colors.primaryVariant

    Box(
        modifier = Modifier
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = text, color = contentColor)
        }
    }
}

@Preview
@Composable
fun AddButtonPrev(){
    AddButton(onClick = {}, text = "While")
}