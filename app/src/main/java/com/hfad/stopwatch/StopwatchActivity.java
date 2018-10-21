package com.hfad.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds=0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState !=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    //Start the stopwatch running when the start button is clicked
    public void onClickStart(View view){

        running=true;
    }

    //Stop the stopwatch runnign when the stop button is clicked
    public void onClickStop(View view){

        running=false;
    }

    //Reset the stopwatch when the reset button is clicked
    public void onClickReset(View view){
        running=false;
        seconds=0;
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        //needed to keep the time ticking
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }

        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onStop(){
        super.onStop();
        wasRunning=running;
        running=false;
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(wasRunning){
            running=true;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        wasRunning=running;
        running=false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running=true;
        }
    }

}
