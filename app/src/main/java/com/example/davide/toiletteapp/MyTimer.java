package com.example.davide.toiletteapp;

import android.util.Log;

import java.util.TimerTask;

public class MyTimer extends TimerTask {

    private static StatusClass statusClass = MainActivity.getStatusClass();


    @Override
    public void run() {
        String value = TryThread.getStatus();

        if( value != null) {
            statusClass.setStatus(value);

        }else {
            statusClass.setStatus("Nessuno")   ;
        }
    }
}
