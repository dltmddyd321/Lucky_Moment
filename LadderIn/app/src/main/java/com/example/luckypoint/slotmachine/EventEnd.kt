package com.example.luckypoint.slotmachine

//MainActivity에서 결과 값과 횟수를 기록할 인터페이스 선언
interface EventEnd {
    fun eventEnd(result:Int, count:Int)
}