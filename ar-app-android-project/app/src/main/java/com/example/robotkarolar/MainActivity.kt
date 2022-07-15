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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.robotkarolar.ui.theme.RobotKarolArTheme
import com.example.robotkarolar.uiviews.ChallengeView
import com.example.robotkarolar.uiviews.models.CodeViewModel
import com.example.robotkarolar.uiviews.HomeView

class MainActivity : ComponentActivity() {

    private var inChallangeView = false

    override fun onBackPressed() {
        if(!inChallangeView) {
            this.moveTaskToBack(true)
        }
    }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RobotKarolArTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column {

                        val navController = rememberNavController()
                        val viewModel: CodeViewModel = CodeViewModel()
                        NavHost(navController = navController, startDestination = "free") {
                            composable("free") { HomeView(navController = navController, viewModel = viewModel); inChallangeView = false }
                            composable("challenge") { ChallengeView(navController = navController, viewModel = viewModel); inChallangeView = true}
                        }

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