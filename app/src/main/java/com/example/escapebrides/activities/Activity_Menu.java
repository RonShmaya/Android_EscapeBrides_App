package com.example.escapebrides.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.escapebrides.R;
import com.example.escapebrides.tools.DataManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class Activity_Menu extends AppCompatActivity {
    public static final String EXTRA_GAME_TYPE = "EXTRA_GAME_TYPE";
    private MaterialTextView menu_LBL_title;
    private MaterialButton menu_BTN_arrow_play;
    private MaterialButton menu_BTN_sensor_play;
    private MaterialButton menu_BTN_top_10;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();
        init_actions();
    }

    private void init_actions() {
        menu_BTN_arrow_play.setOnClickListener(view -> go_next(Activity_game.class , DataManager.GAME_TYPE.Arrows.toString()));

        menu_BTN_sensor_play.setOnClickListener(view -> go_next(Activity_game.class , DataManager.GAME_TYPE.Sensors.toString()));

        menu_BTN_top_10.setOnClickListener(view -> {
            go_next(Activity_top_10.class , "top_10");
        });


    }

    private <T extends AppCompatActivity> void go_next(Class<T> nextActivity , String type) {
        Intent intent = new Intent(this, nextActivity);
        bundle.putString(EXTRA_GAME_TYPE , type);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void findViews() {
        bundle = getIntent().getExtras();
        String userName = bundle.getString("EXTRA_USER_NAME", "null");
        menu_LBL_title = findViewById(R.id.menu_LBL_title);
        menu_LBL_title.setText("Welcome " + userName);
        menu_BTN_arrow_play = findViewById(R.id.menu_BTN_arrow_play);
        menu_BTN_sensor_play = findViewById(R.id.menu_BTN_sensor_play);
        menu_BTN_top_10 = findViewById(R.id.menu_BTN_top_10);
    }
}