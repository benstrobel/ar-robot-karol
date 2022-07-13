package com.example.robotkarolar.uiviews.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.robotkarolar.R

@Composable
fun CodeNavButton(onClick: () -> Unit, icon: Int, modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(5.dp)
            .size(40.dp)
            .clip(CircleShape)
            //.clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
    }
}

@Preview
@Composable
fun CodeNavButtonPreview() {
    Row() {
        CodeNavButton({}, R.drawable.up)
        CodeNavButton({}, R.drawable.down)
    }
}