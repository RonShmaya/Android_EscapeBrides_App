package com.example.escapebrides;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class DataManager { //TODO: change to singelton on version 2
    private HashMap<Moving.Direction, Integer> directions_groom_sources;
    private HashMap<Moving.Direction, Integer> directions_bride_sources;
    public static final int EMPTY_RESOURCE = 0; //TODO: remove static after changing class to singelton
    private final int groom_up_id = R.drawable.ic_groom_up;
    private final int groom_down_id =  R.drawable.ic_groom_down;
    private final int groom_left_id = R.drawable.ic_groom_left;
    private final int groom_right_id =  R.drawable.ic_groom_right;
    private final int bride_up_id =  R.drawable.ic_bride_up;
    private final int bride_down_id =  R.drawable.ic_bride_down;
    private final int bride_left_id =  R.drawable.ic_bride_left;
    private final int bride_right_id =  R.drawable.ic_bride_right;
    private final int enemy_catch_id =  R.drawable.ic_heart_brake;
    private final String posotion_id = "main_IMG_matrix_";
    private AppCompatActivity activity;

    public DataManager() {
        set_map_values();
    }
    public DataManager(AppCompatActivity activity) {
        this();
        this.activity = activity;
    }

    private void set_map_values() {
        directions_groom_sources = new HashMap<>();
        directions_groom_sources.put(Moving.Direction.up, groom_up_id);
        directions_groom_sources.put(Moving.Direction.down,groom_down_id);
        directions_groom_sources.put(Moving.Direction.left,groom_left_id);
        directions_groom_sources.put(Moving.Direction.right, groom_right_id);

        directions_bride_sources = new HashMap<>();
        directions_bride_sources.put(Moving.Direction.up, bride_up_id);
        directions_bride_sources.put(Moving.Direction.down,bride_down_id);
        directions_bride_sources.put(Moving.Direction.left,bride_left_id);
        directions_bride_sources.put(Moving.Direction.right, bride_right_id);

    }
    public int get_groom_resource(Moving.Direction direction){
        return directions_groom_sources.getOrDefault(direction , EMPTY_RESOURCE);
    }
    public int get_bride_resource(Moving.Direction direction){
        return directions_bride_sources.getOrDefault(direction , EMPTY_RESOURCE);
    }
    public int get_enemy_catch_id(){
        return enemy_catch_id;
    }
    public ImageView get_position_source(int row,int col){
        return activity.findViewById(activity.getResources().getIdentifier(posotion_id+row+col, "id", activity.getPackageName()));
    }



}
