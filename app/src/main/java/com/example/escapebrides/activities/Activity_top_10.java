package com.example.escapebrides.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.escapebrides.R;
import com.example.escapebrides.callBacks.CallBack_location;
import com.example.escapebrides.fragments.Fragment_Details;
import com.example.escapebrides.fragments.Fragment_map;
import com.example.escapebrides.tools.DataManager;
import com.example.escapebrides.tools.GameServices;
import com.example.escapebrides.tools.MySP;
import com.google.android.material.appbar.MaterialToolbar;

public class Activity_top_10 extends AppCompatActivity {
    private Fragment_Details fragment_detailes;
    private Fragment_map fragment_map;
    private final String TITLE = "Escape Brides";
    private final String SUB_TITLE = "Wait For The Right One";
    private MaterialToolbar toolbar;
    private boolean is_sound_on;
    private MenuItem sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);
        findViews();

        fragment_map = new Fragment_map();
        fragment_detailes = new Fragment_Details((lat, lng, tag_name) -> fragment_map.get_location(lat, lng, tag_name));

        getSupportFragmentManager().beginTransaction().add(R.id.top_LAY_details, fragment_detailes).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.top_LAY_maps, fragment_map).commit();
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            finish();
        });
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
        GameServices.getGameServices().sound_handler(sound, false, false);
        GameServices.getGameServices().save_to_sp_sound(is_sound_on);
    }

    @Override
    protected void onStart() {
        super.onStart();
        is_sound_on = MySP.get_my_SP().getBoolFromSP(DataManager.EXTRA_SOUND_STATE, true);
        if(sound != null)
            GameServices.getGameServices().sound_handler(sound, is_sound_on , false);
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



    @Override
    public void finish() {
        GameServices.getGameServices().sound_handler(sound ,is_sound_on, true);
        super.finish();
    }
}