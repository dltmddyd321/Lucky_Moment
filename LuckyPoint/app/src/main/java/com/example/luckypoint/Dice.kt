package com.example.luckypoint

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Dice : AppCompatActivity() {

    private val diceTextView: TextView by lazy {
        findViewById(R.id.diceTextView)
    }

    private val diceButton: Button by lazy {
        findViewById(R.id.diceButton)
    }

    private val diceImageView: ImageView by lazy {
        findViewById(R.id.diceImageView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)

        diceButton.setOnClickListener { //주사위 던지기 버튼을 클릭하면

            //주사위의 결과가 1~6까지 랜덤하게 나오는 변수 지정
            var diceNumber = ((Math.random() * 6) + 1).toInt()

            //주사위 결과 TextView에 랜덤하게 나온 결과를 연결
            diceTextView.text = diceNumber.toString()

            when (diceNumber) {
                //1 ~ 6까지 각 수에 알맞는 주사위 이미지가 배치
                1 -> diceImageView.setImageResource(R.drawable.ic_dice_one)
                2 -> diceImageView.setImageResource(R.drawable.ic_dice_two)
                3 -> diceImageView.setImageResource(R.drawable.ic_dice_three)
                4 -> diceImageView.setImageResource(R.drawable.ic_dice_four)
                5 -> diceImageView.setImageResource(R.drawable.ic_dice_five)
                6 -> diceImageView.setImageResource(R.drawable.ic_dice_six)

            }
        }
    }
}