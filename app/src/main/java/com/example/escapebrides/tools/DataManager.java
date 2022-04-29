package com.example.escapebrides.tools;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.escapebrides.R;
import com.example.escapebrides.game_components.Moving;
import com.example.escapebrides.game_components.PlayerDetails;
import com.example.escapebrides.game_components.TopDetails;
import com.google.gson.Gson;

import java.util.HashMap;

public class DataManager {
    private static DataManager _instance = new DataManager();
    public enum GAME_TYPE {
        Arrows , Sensors
    };
    public static final int EMPTY_RESOURCE = 0;
    private static final String EXTRA_USERS_DETAILS = "EXTRA_USER_DETAILS";
    public static final String EXTRA_SOUND_STATE = "EXTRA_SOUND_STATE";
    private GAME_TYPE game_type;
    private HashMap<Moving.Direction, Integer> directions_groom_sources;
    private HashMap<Moving.Direction, Integer> directions_bride_sources;
    private final int groom_up_id = R.drawable.ic_groom_up;
    private AppCompatActivity game;
    private final int groom_down_id =  R.drawable.ic_groom_down;
    private final int groom_left_id = R.drawable.ic_groom_left;
    private final int groom_right_id =  R.drawable.ic_groom_right;
    private final int bride_up_id =  R.drawable.ic_bride_up;
    private final int bride_down_id =  R.drawable.ic_bride_down;
    private final int bride_left_id =  R.drawable.ic_bride_left;
    private final int bride_right_id =  R.drawable.ic_bride_right;
    private final int enemy_catch_id =  R.drawable.ic_heart_brake;
    private final int coin_catch_id =  R.drawable.ic_money_take;
    private final int coin_regular_id = R.drawable.ic_money;
    private final String posotion_id_by_arrows = "game_op_1_IMG_matrix_";
    private final String posotion_id_by_sensors = "game_op_2_IMG_matrix_";

    private DataManager() { set_map_values(); }

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
    public int get_coin_id(){
        return coin_regular_id;
    }
    public int get_coin_taken_id(){
        return coin_catch_id;
    }

    public DataManager setGameActive(GAME_TYPE game_type){
        this.game_type = game_type;
        return this;
    }
    public DataManager.GAME_TYPE getGameActive(){
        return this.game_type;
    }

    public DataManager setGame(AppCompatActivity game) {
        this.game = game;
        return this;
    }

    public ImageView get_position_source(int row, int col){
        String view_path = "";
        if(game_type == GAME_TYPE.Arrows)
            view_path = posotion_id_by_arrows;
        else
            view_path = posotion_id_by_sensors;

        return game.findViewById(game.getResources().getIdentifier(view_path +row+col, "id", game.getPackageName()));
    }

    public void update_top_10(PlayerDetails playerDetails) {
        //get
        TopDetails topDetails = get_top_10();

        //update
        topDetails.update_top(playerDetails);

        //save
        String top_string = new Gson().toJson(topDetails);
        MySP.get_my_SP().putStringToSP(EXTRA_USERS_DETAILS , top_string);
    }
    public TopDetails get_top_10() {
        String top_string = MySP.get_my_SP().getStringFromSP(EXTRA_USERS_DETAILS , "{}");
        TopDetails topDetails = new Gson().fromJson(top_string , TopDetails.class);
        return topDetails;
    }

    public static DataManager getDataManager(){
        return _instance;
    }



}
