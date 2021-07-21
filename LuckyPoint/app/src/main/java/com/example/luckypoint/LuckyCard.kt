package com.example.luckypoint

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlin.random.Random

class LuckyCard : AppCompatActivity() {

    private val imageViews by lazy { arrayOfNulls<ImageView>(4) }
    private val cardImg = intArrayOf(R.drawable.card_0, R.drawable.card_1, R.drawable.card_2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_card)

        findViewById<Button>(R.id.openBtn).setOnClickListener(onButtonClick)

        for (i in 0..3) {
            imageViews[i] = findViewById(R.id.firstCard + i)
        }
    }

    private var onButtonClick = View.OnClickListener { view ->
        if(view.id == R.id.openBtn) {
            setGameResult()
        }
    }

    private fun setGameResult() {
        var cardSum = 2
        for(i in 0..3) {
            val num = Random.nextInt(3)
            cardSum += num
            imageViews[i]?.setImageResource(cardImg[num])
        }
    }
}