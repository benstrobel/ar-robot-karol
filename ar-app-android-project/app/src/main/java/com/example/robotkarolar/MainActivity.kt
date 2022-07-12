package com.example.robotkarolar

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.robotkarolar.ui.theme.RobotKarolArTheme
import com.example.robotkarolar.uiviews.CodeView
import com.example.robotkarolar.uiviews.CodeViewModel
import com.example.robotkarolar.uiviews.HomeView

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RobotKarolArTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column {
                        val viewModel: CodeViewModel = CodeViewModel()
                        HomeView(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    MainActivity()
}