package com.example.luckypoint

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlin.random.Random

class LuckyCard : AppCompatActivity() {

    //카드 이미지들을 담을 배열을 지정하고, 그 배열안에 들어갈 이미지 요소 선언
    private val imageViews by lazy { arrayOfNulls<ImageView>(4) }
    private val cardImg = intArrayOf(R.drawable.card_0, R.drawable.card_1, R.drawable.card_2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_card)

        findViewById<Button>(R.id.openBtn).setOnClickListener(onButtonClick)

        for (i in 0..3) {
            //카드 이미지를 담은 imageViews 배열을 순회
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
            //랜덤하게 선택된 카드의 인덱스가 변수로 지정
            val num = Random.nextInt(3)
            cardSum += num

            //변수 값에 따라 해당 인덱스에 맞는 이미지가 배치
            imageViews[i]?.setImageResource(cardImg[num])
        }
    }
}