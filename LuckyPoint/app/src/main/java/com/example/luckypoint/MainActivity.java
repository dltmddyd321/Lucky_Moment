package com.example.luckypoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button roulette, card, dice, ladder;
    ImageButton lotto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roulette = (Button) findViewById(R.id.button1);
        card = (Button) findViewById(R.id.button2);
        dice = (Button) findViewById(R.id.button3);
        ladder = (Button) findViewById(R.id.button4);
        lotto = (ImageButton) findViewById(R.id.lottoBtn);
    }
}