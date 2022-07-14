package com.example.robotkarolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.robotkarolar.ui.theme.RobotKarolArTheme
import com.example.robotkarolar.uiviews.models.CodeViewModel
import com.example.robotkarolar.uiviews.HomeView

class MainActivity : ComponentActivity() {

    override fun onBackPressed() {
        this.moveTaskToBack(true);
    }

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