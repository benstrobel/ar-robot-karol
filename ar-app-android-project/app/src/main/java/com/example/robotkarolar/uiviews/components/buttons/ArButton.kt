package com.example.robotkarolar.uiviews.components.buttons

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.robotkarolar.ArActivity
import com.example.robotkarolar.R
import com.example.robotkarolar.karollogic.instructions.visitors.ExpressionSanityVisitor
import com.example.robotkarolar.uiviews.models.CodeViewModel

@Composable
fun ArButton(viewModel: CodeViewModel) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Show in Room",
            style = MaterialTheme.typography.subtitle1,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center
        )

        CodeNavButton(
            onClick = {
                val sanityVisitor = ExpressionSanityVisitor()
                viewModel.codeBlock.value.accept(sanityVisitor)
                if(sanityVisitor.isSane) {
                    val intent = Intent(context, ArActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    val bundle = Bundle()
                    bundle.putParcelable("codeBlock", viewModel.codeBlock.value)

                    //challenge
                    bundle.putInt("challengeNumber", viewModel.currentChallenge.value)

                    intent.putExtras(bundle)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Please select expressions for all \"PICK\" blocks", Toast.LENGTH_LONG).show()
                }
            },
            icon = R.drawable.playbutton
        )
    }
}

@Preview
@Composable
fun ArButtonPreview() {
    var viewModel = CodeViewModel()
    ArButton(viewModel = viewModel)
}