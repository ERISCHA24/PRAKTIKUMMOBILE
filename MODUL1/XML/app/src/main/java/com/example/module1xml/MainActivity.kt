package com.example.module1xml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var leftDiceView: ImageView
    private lateinit var rightDiceView: ImageView
    private lateinit var btnActionRoll: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListener()
    }

    private fun initViews() {
        leftDiceView = findViewById(R.id.iv_left_dice)
        rightDiceView = findViewById(R.id.iv_right_dice)
        btnActionRoll = findViewById(R.id.btn_action_roll)
    }

    private fun setupListener() {
        btnActionRoll.setOnClickListener {
            executeDiceRoll()
        }
    }

    private fun executeDiceRoll() {
        val firstValue = (1..6).random()
        val secondValue = (1..6).random()

        leftDiceView.setImageResource(resolveDiceDrawable(firstValue))
        rightDiceView.setImageResource(resolveDiceDrawable(secondValue))

        val resultMessage = if (firstValue == secondValue) {
            getString(R.string.double_message)
        } else {
            getString(R.string.bad_message)
        }

        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show()
    }

    private fun resolveDiceDrawable(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}