package com.example.luckypoint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class PickNumDice : AppCompatActivity() {

    private lateinit var diceTextView: TextView
    private lateinit var diceImageView: ImageView
    private lateinit var diceNumberInputView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_num_dice)

        diceTextView = findViewById(R.id.tvDice)
        diceImageView = findViewById(R.id.imageView)
        diceNumberInputView = findViewById(R.id.editText)

    }

    fun onRollClick(v: View) {

        var diceNumber = Random.nextInt(1, 7)

        diceTextView.setText("주사위 번호 : $diceNumber")

        when (diceNumber) {
            1 -> diceImageView.setImageResource(R.drawable.ic_dice_one)
            2 -> diceImageView.setImageResource(R.drawable.ic_dice_two)
            3 -> diceImageView.setImageResource(R.drawable.ic_dice_three)
            4 -> diceImageView.setImageResource(R.drawable.ic_dice_four)
            5 -> diceImageView.setImageResource(R.drawable.ic_dice_five)
            6 -> diceImageView.setImageResource(R.drawable.ic_dice_six)
        }

        var inputValue = diceNumberInputView.getText().toString()

        if (inputValidatationCheck(inputValue)) {
            // 주사위와 입력한 값이 같은지 검사
            if (diceNumber == inputValue.toInt()) {
                showToast("정답입니다!")
            } else {
                showToast("땡! 틀렸네요. ㅋㅋ")
            }
        } else {
            showToast("입력값이 잘못되었습니다.")
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun inputValidatationCheck(inputValue: String): Boolean {
        try {
            // 입력값이 없는 경우
            if (inputValue.isEmpty()) return false
            // 입력값이 1보다 작거나 6보다 큰 경우
            else if (inputValue.toInt() < 1 || inputValue.toInt() > 6) return false
        } catch (e: Exception) {
            // 숫자가 입력되지 않은 경우
            return false
        }
        return true
    }

}