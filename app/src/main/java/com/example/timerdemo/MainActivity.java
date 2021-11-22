package com.example.timerdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    TextView textView;
    SeekBar seekBar;
    boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void myResetTimer(){
        counterIsActive = false;
        goButton.setText("GO!");
        countDownTimer.cancel();
        textView.setText("0:30");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
    }
    public void myGo(View view){
        if(counterIsActive){
            myResetTimer();
        }else{
            counterIsActive = true;
            seekBar.setEnabled(false);
            goButton.setText("STOP");
           countDownTimer = new CountDownTimer(seekBar.getProgress()*1000+100,1000){
                public void onTick(long ms){

                    updateTimer((int)ms/1000);
                }
                public  void  onFinish(){

                    mp.start();
                    myResetTimer();
                }
            }.start();
        }

    }
    public void updateTimer(int progress){
        int minutes = progress/60;
        int seconds = progress - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if(seconds <= 9){
            secondString = "0"+secondString;
        }
        textView.setText(Integer.toString(minutes) + ":" + secondString);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this,R.raw.carhorn);
        goButton = findViewById(R.id.goButton);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}