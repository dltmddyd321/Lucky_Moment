package com.example.luckypoint.slotmachine

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.luckypoint.R
import kotlinx.android.synthetic.main.activity_slot_machine.*
import kotlin.random.Random

class SlotMachine : AppCompatActivity(), EventEnd {

    internal var count_down = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slot_machine)

        //Activity 가로 형태로 기본 설정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        //Frame Layout의 요소를 실제 MainActivity에서 업데이트
        image1.setEventEnd(this@SlotMachine)
        image2.setEventEnd(this@SlotMachine)
        image3.setEventEnd(this@SlotMachine)

        ptUp.setOnClickListener {
            if (Common.SCORE >= 50) {
                //Up 손잡이를 클릭하면 Down 손잡이가 시각화
                ptUp.visibility = View.GONE
                ptDown.visibility = View.VISIBLE

                //image1, 2, 3에 대한 각각의 이미지 슬롯이 랜덤으로 이미지 값을 결정
                image1.setValueRandom(Random.nextInt(6), Random.nextInt(15-5+1)+5)

                image2.setValueRandom(Random.nextInt(6), Random.nextInt(15-5+1)+5)

                image3.setValueRandom(Random.nextInt(6), Random.nextInt(15-5+1)+5)

                //슬롯을 1번 돌리면 기존 점수 50점 차감
                Common.SCORE -= 50
                text_score.text = Common.SCORE.toString()
            } else {
                //점수가 0점이라면 부족 메시지 출력
                Toast.makeText(this,"You not enough money.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun eventEnd(result: Int, count: Int) {
        if(count_down < 2) {
            count_down ++
        } else {
            //초기 상태에서는 Down 손잡이 이미지가 안보이도록 설정
            ptDown.visibility = View.GONE
            ptUp.visibility = View.VISIBLE

            count_down = 0

            if(image1.value == image2.value && image2.value == image3.value) {
                //3개의 이미지 슬롯이 모두 같은 결과일 경우 토스트 메시지와 점수 300점을 부여
                Toast.makeText(this,"띵동! 행운이네요~",Toast.LENGTH_SHORT).show()
                Common.SCORE += 300
                text_score.text = Common.SCORE.toString()
            } else if(image1.value == image2.value || image2.value == image3.value || image1.value == image3.value) {
                //2개의 이미지 슬롯이 같은 결과일 경우 토스트 메시지와 점수 70점을 부여
                Toast.makeText(this,"아이고! 조금 아쉽네요.",Toast.LENGTH_SHORT).show()
                Common.SCORE += 70
                text_score.text = Common.SCORE.toString()
            } else {
                //하나도 같은 이미지 슬롯이 아닐 경우 토스트 메시지만을 출력
                Toast.makeText(this,"운이 없군요. ㅋㅋ",Toast.LENGTH_SHORT).show()
            }
        }
    }
}