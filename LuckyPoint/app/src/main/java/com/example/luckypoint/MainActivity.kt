package com.example.luckypoint

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val rouletteBtn: Button by lazy {
        findViewById(R.id.button1)
    }

    private val cardBtn: Button by lazy {
        findViewById(R.id.button2)
    }

    private val diceBtn: Button by lazy {
        findViewById(R.id.button3)
    }

    private val ladderBtn: Button by lazy {
        findViewById(R.id.button4)
    }

    private val lottoBtn: ImageButton by lazy {
        findViewById(R.id.lottoBtn)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rouletteBtn.setOnClickListener {
            val intent = Intent(this, Roulette::class.java)
            startActivity(intent)
        }

        cardBtn.setOnClickListener {
            val intent = Intent(this, LuckyCard::class.java)
            startActivity(intent)
        }
        
        diceBtn.setOnClickListener { 
            val intent = Intent(this, Dice::class.java)
            startActivity(intent)
        }

        lottoBtn.setOnClickListener {
            val intent = Intent(this, Lotto::class.java)
            startActivity(intent)
        }

    }
}