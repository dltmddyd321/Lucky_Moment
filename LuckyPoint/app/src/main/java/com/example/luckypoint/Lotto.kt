package com.example.luckypoint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class Lotto : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clear)
    } //초기화 버튼 연결

    private val restartButton: Button by lazy {
        findViewById<Button>(R.id.restartBtn)
    } //재시작 버튼 연결

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    } //추가 버튼 연결

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runbtn)
    } //실행 버튼 연결

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numpick)
    } //numberPicker의 실행 범위 지정 및 사용을 위한 연결

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>( //여러 개의 TextView를 담을 리스트
                findViewById<TextView>(R.id.txt1),
                findViewById<TextView>(R.id.txt2),
                findViewById<TextView>(R.id.txt3),
                findViewById<TextView>(R.id.txt4),
                findViewById<TextView>(R.id.txt5),
                findViewById<TextView>(R.id.txt6)
        )
    }

    private var didRun = false //예외 처리를 위한 상태 변수

    private val pickNumberSet = hashSetOf<Int>()
    //중복을 방지하는 컬렉션 Set 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)

        restartButton.setOnClickListener {
            val intent = Intent(this, Lotto::class.java)
            startActivity(intent)
        }

        numberPicker.minValue = 1
        numberPicker.maxValue = 45
        // nP의 범위를 1 ~ 45까지 지정

        initRunButton()
        initAddButton()
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()
            //버튼을 클릭 시 랜덤의 숫자를 추출하여 리스트를 생성

            didRun = true

            list.forEachIndexed { index, number ->
                //어떤 인덱스가 넘어오는지 알기 위해 index, number 둘 다 반환
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.visibility = View.VISIBLE
                setNumberBackground(number, textView)
            }
        }
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if(didRun) { //초기화 요구
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 5) { //최대 선택 가능 번호 수 초과
                Toast.makeText(this, "번호는 최개 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)) { //nP에 값이 이미 존재한다면
                Toast.makeText(this, "번호는 최개 5개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
            }

            val textView = numberTextViewList[pickNumberSet.size] //Set의 size가 곧 현재 값의 위치.
            textView.visibility = View.VISIBLE // 시각화
            textView.text = numberPicker.value.toString() // text에 데이터 받아오기

            setNumberBackground(numberPicker.value, textView)

            pickNumberSet.add(numberPicker.value) //Set에 선택된 값 등록
        }
    }

    private fun setNumberBackground(number:Int, textView: TextView){ //로또 숫자 범위 별 색 지정
        when(number) {
            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yello)
            //drawble 값을 호출하여 배경으로 지정한다.
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear() //Set 내용 비우기
            numberTextViewList.forEach {
                it.visibility = View.GONE // 내용이 비워지면 다시 비시각화
            }
            didRun = false
        }
    }

    private fun getRandomNumber(): List<Int> { //Int형 List를 반환하는 함수
        val numberList = mutableListOf<Int>() //리스트 객체 생성
                .apply { // 1 ~ 45를 넣기 위한 apply
                    for (i in 1..45) { //반복문
                        if(pickNumberSet.contains(i)) {
                            continue //이미 뽑힌 숫자가 나온다면 건너뛴다.
                        }
                        this.add(i)
                    }
                }

        numberList.shuffle()
        //랜덤 기능을 위해 리스트의 내용을 섞음.

        val newList = pickNumberSet.toList() + numberList.subList(0,6 - pickNumberSet.size)
        //0 ~ 6까지 내용이 선택되는 서브 리스트를 상위 리스트에서 뽑아 newList에 저장
        //새로운 리스트를 추가하여 새로운 로또 세트를 만들 수 있음.

        return newList.sorted()
        //랜덤하게 뽑힌 숫자들이 newList안에서 오름차순으로 정렬
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    //뒤로 가기 버튼 누르면 메인화면으로 복귀
}