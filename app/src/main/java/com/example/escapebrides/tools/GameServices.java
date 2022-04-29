package com.example.escapebrides.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.escapebrides.R;
import com.example.escapebrides.callBacks.CallBack_changeDirection;

public class GameServices {
    private static GameServices _instance = null;
    public static int MY_PERMISSIONS_REQUEST_LOCATION =1;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor sensor;
    private CallBack_changeDirection changeDirection;
    private Context context;
    private MediaPlayer mediaPlayer;


    private GameServices(Context context) {
        this.context = context.getApplicationContext();
        mediaPlayer = MediaPlayer.create(this.context, R.raw.sound_background);
        mediaPlayer.setLooping(true);

    }

    public static void initHelper(Context context) {
        if (_instance == null) {
            _instance = new GameServices(context);
        }
    }


    public GameServices sensors_up() {
        sensorManager = (SensorManager) context.getSystemService(this.context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        activate_sensor();
        return this;
    }


    public void makeCatchVibrate(){
            Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
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
    public void makeQuickVibrate(){
        Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {50 , 10 , 25 , 10 };
        int[] strengh = {1 , 20 , 1 , 20 };
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            VibrationEffect viberEffect = VibrationEffect.createWaveform(pattern , strengh, -1);
            vib.vibrate(viberEffect);
        }
        else{
            vib.vibrate(pattern,-1);
        }
    }
    public void setCallBack_stepDetector(CallBack_changeDirection changeDirection) {
        this.changeDirection = changeDirection;
    }
    public void activate_sensor(){
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(changeDirection != null)
                    changeDirection.changeDirection(sensorEvent.values[0] , sensorEvent.values[1]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

    }
    public static GameServices getGameServices(){
        return _instance;
    }

    public void register() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregister() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    public void sound_handler(MenuItem sound_item ,  boolean isSoundOn, boolean needed_save_to_sp) {
        if (isSoundOn) {
            sound_item.setIcon(android.R.drawable.ic_lock_silent_mode_off);
            sound_on();
        } else {
            sound_item.setIcon(android.R.drawable.ic_lock_silent_mode);
            sound_off();
        }

        if(needed_save_to_sp)
            save_to_sp_sound(isSoundOn);

    }
    public void save_to_sp_sound(boolean isSoundOn){
        MySP.get_my_SP().putBoolToSP(DataManager.EXTRA_SOUND_STATE , isSoundOn);
    }
    public void sound_on(){
        mediaPlayer.start();
    }
    public void sound_off(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }




}
