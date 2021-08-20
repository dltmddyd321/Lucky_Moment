package com.example.slotmachine

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.slotmachine.ImageViewScroll.EventEnd
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), EventEnd {

    internal var count_down = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        image1.setEventEnd(this@MainActivity)
        image2.setEventEnd(this@MainActivity)
        image3.setEventEnd(this@MainActivity)

        ptUp.setOnClickListener {
            if (Common.SCORE >= 50) {
                ptUp.visibility = View.GONE
                ptDown.visibility = View.VISIBLE

                image1.setValueRandom(Random.nextInt(6), Random.nextInt(15-5+1)+5)

                image2.setValueRandom(Random.nextInt(6), Random.nextInt(15-5+1)+5)

                image3.setValueRandom(Random.nextInt(6), Random.nextInt(15-5+1)+5)

                Common.SCORE -= 50
                text_score.text = Common.SCORE.toString()
            } else {
                Toast.makeText(this,"You not enough money.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun eventEnd(result: Int, count: Int) {
        if(count_down < 2) {
            count_down ++
        } else {
            ptDown.visibility = View.GONE
            ptUp.visibility = View.VISIBLE

            count_down = 0

            if(image1.value == image2.value && image2.value == image3.value) {
                Toast.makeText(this,"띵동! 행운이네요~",Toast.LENGTH_SHORT).show()
                Common.SCORE += 300
                text_score.text = Common.SCORE.toString()
            } else if(image1.value == image2.value || image2.value == image3.value || image1.value == image3.value) {
                Toast.makeText(this,"아이고! 조금 아쉽네요.",Toast.LENGTH_SHORT).show()
                Common.SCORE += 300
                text_score.text = Common.SCORE.toString()
            } else {
                Toast.makeText(this,"운이 없군요. ㅋㅋ",Toast.LENGTH_SHORT).show()
            }
        }
    }
}