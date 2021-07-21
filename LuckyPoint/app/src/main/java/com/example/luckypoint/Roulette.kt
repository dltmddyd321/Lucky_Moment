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
                binding.resultRoulette.text = "결과는??"
            }

            override fun onRotateEnd(result: String) {
                binding.resultRoulette.text = "$result!!"
            }
        }
        val toDegrees = (2000..10000).random().toFloat()
        binding.rouletteSystem.rotateRoulette(toDegrees,3000,rotateListener)
    }
}