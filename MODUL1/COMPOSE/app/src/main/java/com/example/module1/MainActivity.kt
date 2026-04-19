package com.example.module1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoDiceRollerScreen()
        }
    }
}

fun getDiceDrawable(diceValue: Int): Int {
    return when (diceValue) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}

@Preview(showBackground = true)
@Composable
fun DiceAppPreview() {
    TwoDiceRollerScreen()
}

@Composable
fun TwoDiceRollerScreen(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
) {
    var firstDiceValue by remember { mutableStateOf(1) }
    var secondDiceValue by remember { mutableStateOf(1) }
    var statusText by remember { mutableStateOf("") }

    val leftDiceImage = getDiceDrawable(firstDiceValue)
    val rightDiceImage = getDiceDrawable(secondDiceValue)

    val winMessage = stringResource(R.string.double_message)
    val loseMessage = stringResource(R.string.bad_message)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(leftDiceImage),
                contentDescription = "Left Dice: $firstDiceValue"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(rightDiceImage),
                contentDescription = "Right Dice: $secondDiceValue"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                firstDiceValue = (1..6).random()
                secondDiceValue = (1..6).random()

                statusText = if (firstDiceValue == secondDiceValue) {
                    winMessage
                } else {
                    loseMessage
                }
            }
        ) {
            Text(text = stringResource(R.string.roll))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = statusText)
    }
}