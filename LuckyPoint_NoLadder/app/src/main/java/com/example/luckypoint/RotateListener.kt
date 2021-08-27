package com.example.luckypoint

//룰렛 회전 시작과 결과 값을 받아오기 위한 Listener 선언
interface RotateListener {
    fun onRotateStart()
    fun onRotateEnd(result: String)
}