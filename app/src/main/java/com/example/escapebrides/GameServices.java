package com.example.escapebrides;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class GameServices { //TODO: change to singelton on version 2 + send activity once
    AppCompatActivity activity;
    public GameServices() {
    }
    public GameServices(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void makeCatchVibrate(){
            Vibrator vib = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {50 , 500 , 100 , 500};
            int[] strengh = {1 , 200 , 1 , 200};
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                VibrationEffect viberEffect = VibrationEffect.createWaveform(pattern , strengh, -1);
                vib.vibrate(viberEffect);
            }
            else{
                vib.vibrate(pattern,-1);
            }
    }
}
