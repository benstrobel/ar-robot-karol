package com.example.robotkarolar.uiviews

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.robotkarolar.uiviews.components.bars.BottomNavBar
import com.example.robotkarolar.uiviews.models.CodeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChallengeView(navController: NavController, viewModel: CodeViewModel) {
    Scaffold(
        topBar = { Toolbar(topBarText = "ArRobotKarol") }
    ) {

        Column() {
            BottomNavBar(
                navController = navController,
                currentScreenId = viewModel.currentScreen.value.id,
                onItemSelected = {viewModel.currentScreen.value = it}
            )

            ChallengeColum(viewModel = viewModel)
        }
    }
}

@Composable
fun ChallengeColum(viewModel: CodeViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Column(
        Modifier
            .verticalScroll(scrollState)
            .padding(5.dp)
    ){
        ChallengeButton(challengeInt = 1, onClick = {viewModel.changeCurrentChallenge(1); showChallengeSelectionHint(context)}, isSelected = viewModel.currentChallenge.value == 1)
        ChallengeButton(challengeInt = 2, onClick = {viewModel.changeCurrentChallenge(2); showChallengeSelectionHint(context)}, isSelected = viewModel.currentChallenge.value == 2)
        ChallengeButton(challengeInt = 3, onClick = {viewModel.changeCurrentChallenge(3); showChallengeSelectionHint(context)}, isSelected = viewModel.currentChallenge.value == 3)
        ChallengeButton(challengeInt = 4, onClick = {viewModel.changeCurrentChallenge(4); showChallengeSelectionHint(context)}, isSelected = viewModel.currentChallenge.value == 4)
    }
}

private fun showChallengeSelectionHint(context: Context) {
    Toast.makeText(context, "Click \"Show in room\" to see the challenge objective", Toast.LENGTH_SHORT).show()
}

@Composable
fun ChallengeButton(challengeInt: Int, onClick: () -> Unit, isSelected: Boolean) {
    val background = MaterialTheme.colors.primaryVariant.copy(alpha = 0.1f)
    val contentColor = MaterialTheme.colors.primaryVariant

    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = RoundedCornerShape(5.dp)
            )
            .clip(RoundedCornerShape(5.dp))
            .background(background)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(text = "Challenge $challengeInt", color = contentColor)
            
            Spacer(modifier = Modifier.weight(1.0f))
            
            Box(modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Transparent)
                .clickable(onClick = onClick)
                .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector =  if (isSelected) Icons.Outlined.CheckBox else Icons.Outlined.CheckBoxOutlineBlank,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp),
                    tint = contentColor
                )
            }
        }
    }
}

@Preview
@Composable
fun CheckBoxPreview() {
    var viewModel = CodeViewModel()
    ChallengeColum(viewModel = viewModel)
}