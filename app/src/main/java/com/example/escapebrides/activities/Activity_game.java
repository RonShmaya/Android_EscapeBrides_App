package com.example.escapebrides.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.escapebrides.game_components.PlayerDetails;
import com.example.escapebrides.game_components.TopDetails;
import com.example.escapebrides.tools.DataManager;
import com.example.escapebrides.game_components.GameManager;
import com.example.escapebrides.tools.GPS;
import com.example.escapebrides.tools.GameServices;
import com.example.escapebrides.R;
import com.example.escapebrides.tools.MySP;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_game extends AppCompatActivity {
    private Bundle bundle;

    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    public static final String EXTRA_USER_NAME = "EXTRA_USER_NAME";
    public static final String EXTRA_GAME_TYPE = "EXTRA_GAME_TYPE";
    private final String TITLE = "Escape Brides";
    private final String SUB_TITLE = "Wait For The Right One";
    private final int DELAY = 1000;
    private final int SENSOR_GAME_ROWS = 7;
    private final int SENSOR_GAME_COLS = 5;
    private final int ARROWS_GAME_ROWS = 5;
    private final int ARROWS_GAME_COLS = 3;
    private MaterialToolbar toolbar;
    private GameManager game_manager;
    private RelativeLayout game_RLT_startView;
    private MaterialButton game_BTN_start;
    private Timer timer;
    private TIMER_STATUS timer_status = TIMER_STATUS.OFF;
    private String game_type;
    private String player_name;
    private boolean is_sound_on;
    private MenuItem sound;
    private GPS gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        game_type = bundle.getString(EXTRA_GAME_TYPE);
        player_name = bundle.getString(EXTRA_USER_NAME);


        if (game_type.equals(DataManager.GAME_TYPE.Arrows.toString())) {
            setContentView(R.layout.activity_game_option_1);
            findViews_game_arrows();
            init_game_arrows();
        } else if (game_type.equals(DataManager.GAME_TYPE.Sensors.toString())) {
            setContentView(R.layout.activity_game_option_2);
            findViews_game_sensors();
            init_game_sensors();
        } else
            finish(); // page cannot create if never get the game type
        init_tool_bar();
        gps = new GPS(this);
    }

    private void init_tool_bar() {
        toolbar = findViewById(R.id.game_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        is_sound_on = MySP.get_my_SP().getBoolFromSP(DataManager.EXTRA_SOUND_STATE, true);
        if (sound != null)
            GameServices.getGameServices().sound_handler(sound, is_sound_on, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(TITLE);
        toolbar.setSubtitle(SUB_TITLE);
        if (game_type.equals(DataManager.GAME_TYPE.Sensors.toString()))
            GameServices.getGameServices().register();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer_status == TIMER_STATUS.RUNNING) {
            change_timer_status();
        }
        if (game_type.equals(DataManager.GAME_TYPE.Sensors.toString()))
            GameServices.getGameServices().unregister();

        GameServices.getGameServices().sound_handler(sound, false, false);
        GameServices.getGameServices().save_to_sp_sound(is_sound_on);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        sound = menu.findItem(R.id.action_sound);
        GameServices.getGameServices().sound_handler(sound, is_sound_on, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sound) {
            is_sound_on = is_sound_on ? false : true;
            GameServices.getGameServices().sound_handler(sound, is_sound_on, false);
        }
        return super.onOptionsItemSelected(item);
    }

    public void findViews_game_arrows() {
        game_RLT_startView = findViewById(R.id.game_op_1_RLT_startView);
        game_BTN_start = findViewById(R.id.game_op_1_BTN_start);
    }


    public void init_game_arrows() {
        DataManager.getDataManager()
                .setGame(this)
                .setGameActive(DataManager.GAME_TYPE.Arrows);


        game_manager = new GameManager(this, ARROWS_GAME_ROWS, ARROWS_GAME_COLS);
        game_BTN_start.setOnClickListener(view -> Activity_game.this.startGame());

    }

    public void findViews_game_sensors() {
        game_RLT_startView = findViewById(R.id.game_op_2_RLT_startView);
        game_BTN_start = findViewById(R.id.game_op_2_BTN_start);
    }

    public void init_game_sensors() {
        DataManager.getDataManager()
                .setGame(this)
                .setGameActive(DataManager.GAME_TYPE.Sensors);

        GameServices.getGameServices().sensors_up();

        game_manager = new GameManager(this, SENSOR_GAME_ROWS, SENSOR_GAME_COLS);
        game_BTN_start.setOnClickListener(view -> Activity_game.this.startGame());
    }

    protected void startGame() {
        game_RLT_startView.setVisibility(View.INVISIBLE);
        if (timer_status == TIMER_STATUS.OFF) {
            game_manager.start_game();
        }
        change_timer_status();

    }

    protected void change_timer_status() {
        if (timer_status == TIMER_STATUS.OFF) {
            start_timer();
            timer_status = TIMER_STATUS.RUNNING;
        } else if (timer_status == TIMER_STATUS.RUNNING) {
            stop_timer();
            timer_status = TIMER_STATUS.PAUSE;

        } else if (timer_status == TIMER_STATUS.PAUSE) {
            start_timer();
            timer_status = TIMER_STATUS.RUNNING;
        }
    }

    protected void stop_timer() {
        timer.cancel();
        game_RLT_startView.setVisibility(View.VISIBLE);
        game_BTN_start.setText("Continue");
    }

    protected void start_timer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (game_manager.step() == true) {
                        game_over();
                    }
                });

            }
        }, 0, DELAY);

    }

    protected void game_over() {
        Log.d("myLog", "game over...");
       // GPS gps = new GPS(this);
        Intent intent = new Intent(this, Activity_top_10.class);
        intent.putExtras(bundle);
        startActivity(intent);
        save_to_SP(gps);
        finish();
    }

    private void save_to_SP(GPS gps) {
        PlayerDetails playerDetails = new PlayerDetails()
                .setLat(gps.getLat())
                .setLng(gps.getLag())
                .setName(player_name)
                .setScore(this.game_manager.getScore());

        gps.stop_location_track();
        Log.d("myLog" , ""+ playerDetails.getLat() + "----------"+playerDetails.getLng());

        DataManager.getDataManager().update_top_10(playerDetails);

    }

    @Override
    public void finish() {
        GameServices.getGameServices().sound_handler(sound, is_sound_on, true);
        DataManager.getDataManager().setGame(null);
        super.finish();

    }

}