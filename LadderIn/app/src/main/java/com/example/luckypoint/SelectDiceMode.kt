package com.example.luckypoint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectDiceMode : AppCompatActivity() {

    private val normalDice: Button by lazy {
        findViewById(R.id.normalDice)
    }

    private val pickDice: Button by lazy {
        findViewById(R.id.pickDice)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_dice_mode)

        normalDice.setOnClickListener {
            val intent = Intent(this, Dice::class.java)
            startActivity(intent)
        }

        pickDice.setOnClickListener {
            val intent = Intent(this, PickNumDice::class.java)
            startActivity(intent)
        }
    }
}