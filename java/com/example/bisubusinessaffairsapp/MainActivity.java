package com.example.bisubusinessaffairsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button now;
    ProgressBar progressBar;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        now= (Button) findViewById(R.id.enter);
        progressBar= (ProgressBar) findViewById(R.id.progBar);

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                Timer timer=new Timer();
                TimerTask timerTask= new TimerTask() {
                    @Override
                    public void run() {
                        counter++;
                        progressBar.setProgress(counter);
                        if(counter==100){
                            timer.cancel();
                            Intent enter= new Intent(MainActivity.this, login.class);
                            startActivity(enter);
                        }
                    }
                };
                timer.schedule(timerTask, 100,100);
            }
        });
    }
}