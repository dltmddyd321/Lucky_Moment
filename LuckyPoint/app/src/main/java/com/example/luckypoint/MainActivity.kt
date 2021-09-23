package com.example.luckypoint

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.luckypoint.dice.SelectDiceMode
import com.example.luckypoint.slotmachine.SlotMachine

class MainActivity : AppCompatActivity() {

    private val cardBtn: Button by lazy {
        findViewById(R.id.luckyCardBtn)
    }

    private val diceBtn: Button by lazy {
        findViewById(R.id.diceBtn)
    }

    private val slotBtn: Button by lazy {
        findViewById(R.id.slotBtn)
    }

    private val lottoBtn: ImageButton by lazy {
        findViewById(R.id.lottoBtn)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //행운의 카드 뒤집기 화면으로 이동하는 버튼
        cardBtn.setOnClickListener {
            val intent = Intent(this, LuckyCard::class.java)
            startActivity(intent)
        }

        //주사위 모드 선택 화면으로 이동하는 버튼
        diceBtn.setOnClickListener { 
            val intent = Intent(this, SelectDiceMode::class.java)
            startActivity(intent)
        }

        //슬롯 머신 화면으로 이동하는 버튼
        slotBtn.setOnClickListener {
            val intent = Intent(this, SlotMachine::class.java)
            startActivity(intent)
        }

        //로또 화면으로 이동하는 버튼
        lottoBtn.setOnClickListener {
            val intent = Intent(this, Lotto::class.java)
            startActivity(intent)
        }
    }
}