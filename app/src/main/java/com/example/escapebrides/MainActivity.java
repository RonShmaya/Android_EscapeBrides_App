package com.example.escapebrides;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }
    private final String TITLE = "Escape Brides";
    private final String SUB_TITLE = "Wait For The Right One";
    private final int DELAY = 1000;
    private MaterialToolbar toolbar;
    private GameManager game_manager;
    private RelativeLayout main_RLT_startView;
    private MaterialButton main_BTN_start;
    //TODO: set singeltons if needed on version 2
    private Timer timer;
    private TIMER_STATUS timer_status = TIMER_STATUS.OFF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(TITLE);
        toolbar.setSubtitle(SUB_TITLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timer_status == TIMER_STATUS.RUNNING){
            change_timer_status();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } //TODO add items in section two

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    } //TODO add items in section two

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        main_RLT_startView = findViewById(R.id.main_RLT_startView);
        main_BTN_start = findViewById(R.id.main_BTN_start);

    }

    public void init(){
        game_manager = new GameManager(this);
        main_BTN_start.setOnClickListener(view -> MainActivity.this.startGame());
    }

    private void startGame() {
        main_RLT_startView.setVisibility(View.INVISIBLE);
        if(timer_status == TIMER_STATUS.OFF) {
            game_manager.start_game();
        }
        change_timer_status();

    }

    private void change_timer_status() {
        if(timer_status == TIMER_STATUS.OFF){
            start_timer();
            timer_status = TIMER_STATUS.RUNNING;
        }else if(timer_status == TIMER_STATUS.RUNNING){
            stop_timer();
            timer_status = TIMER_STATUS.PAUSE;

        }else if(timer_status == TIMER_STATUS.PAUSE){
            start_timer();
            timer_status = TIMER_STATUS.RUNNING;
        }
    }

    private void stop_timer() {
        timer.cancel();
        main_RLT_startView.setVisibility(View.VISIBLE);
        main_BTN_start.setText("Continue");
    }

    private void start_timer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (game_manager.step() == true) {game_over();}
                });

            }
        }, 0, DELAY);

    }

    private void game_over() {
        Log.d("myLog", "game over...");
        finish();
    }
}