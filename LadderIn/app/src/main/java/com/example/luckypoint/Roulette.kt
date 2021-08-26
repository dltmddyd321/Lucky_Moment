package com.example.luckypoint

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.luckypoint.databinding.ActivityRouletteBinding

class Roulette : AppCompatActivity() {

    private lateinit var binding: ActivityRouletteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_roulette)
        binding.activity = this

        //바인딩을 통해 뷰에 룰렛 그리기
        binding.rouletteSystem.apply {
            setTextColor(R.color.black)
            setRouletteBorderLineColor(R.color.white)

            getTextColor()
            getRouletteBorderLineColor()
        }
    }

    fun rotateRoulette() {
        val rotateListener = object : RotateListener {
            override fun onRotateStart() {
                //룰렛 회전이 시작되면서 결과는?? 텍스트가 출력
                binding.resultRoulette.text = "결과는??"
            }

            override fun onRotateEnd(result: String) {
                //회전이 끝나면 지정한 결과 각도 값에 해당하는 결과 텍스트가 출력
                binding.resultRoulette.text = "$result!!"
            }
        }
        //룰렛이 랜덤한 회전 각도로 얼마동안 회전할 것인지 지정
        val toDegrees = (2000..10000).random().toFloat()
        binding.rouletteSystem.rotateRoulette(toDegrees,3000,rotateListener)
    }
}