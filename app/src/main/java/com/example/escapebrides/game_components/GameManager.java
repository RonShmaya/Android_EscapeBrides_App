package com.example.escapebrides.game_components;

import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.escapebrides.tools.DataManager;
import com.example.escapebrides.tools.GameServices;
import com.example.escapebrides.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class GameManager {
    public final int NUM_OF_LIFE = 3;
    public final int FREQUENCY_COIN = 8;
    private int GAME_ROWS;
    private int GAME_COLS;
    private Life life;
    private Score score;
    private Actor actor;
    private Enemy enemy;
    private Coin coin;
    private Bord bord;
    private Moving move;
    private boolean is_enemy_catched = false;
    private boolean is_actor_catched_coin = false;
    private int coin_counter = 0;
    private AppCompatActivity activity;

    public GameManager() {
    }

    public GameManager(AppCompatActivity activity, int rows, int cols) {
        GAME_ROWS = rows;
        GAME_COLS = cols;
        if (DataManager.getDataManager().getGameActive() == DataManager.GAME_TYPE.Arrows)
            init_arrows(activity);
        else
            init_sensors(activity);

    }

    public void start_game() {
        actor
                .setDirection(Moving.Direction.None)
                .setCode(DataManager.getDataManager().get_groom_resource(Moving.Direction.up));

        enemy
                .setDirection(Moving.Direction.None)
                .setCode(DataManager.getDataManager().get_bride_resource(Moving.Direction.up));

        bord.start_game_positions(actor, enemy);
    }

    public boolean step() {
        if (is_enemy_catched) {
            is_enemy_catched = false;
            return exec_enemy_catched();
        }
        if(is_actor_catched_coin){
            bord.set_charactor_view(actor , actor.getCode());
            is_actor_catched_coin = false;
        }
        if (coin_counter == FREQUENCY_COIN)
            change_coin_place();

        enemy.rand_direction();
        bord.next_character(enemy);
        bord.next_character(actor);

        if (bord.is_catching(actor, enemy)) {
            exe_boom();
            is_enemy_catched = true;
            return false;
        }
        if(bord.is_catching(actor , coin))
            exe_coin_catch();

        if(bord.is_catching(enemy , coin)){
            bord.set_charactor_view(coin , enemy.getCode());
            coin.setCol(-1).setRow(-1);
        }

        coin_counter++;
        exec_enemy_not_catching();
        is_enemy_catched = false;
        return false;
    }

    private void change_coin_place() {
        ArrayList<Integer> places;
        int row;
        int col;
        do {
            places = coin.rand_place();
            row = places.get(0); // row
            col = places.get(1); // col
        }
        while (bord.index_taken_by_indexes(row, col));
        coin.setRow(row).setCol(col);
        bord.set_character_movement_on_bord(coin);
        coin_counter = 0;
    }

    private void exe_coin_catch() {
        bord.set_charactor_view(coin , DataManager.getDataManager().get_coin_taken_id());
        score.add_score(10);
        coin.setCol(-1).setRow(-1);
        GameServices.getGameServices().makeQuickVibrate();
        is_actor_catched_coin = true;
    }

    private void exe_boom() {
        bord.set_charactor_view(enemy, 0);
        bord.set_charactor_view(actor, DataManager.getDataManager().get_enemy_catch_id());
        GameServices.getGameServices().makeCatchVibrate();
    }

    private void exec_enemy_not_catching() {
        Log.d("myLog", "enemy nottt catching...");
        score.add_score(1);
    }

    private boolean exec_enemy_catched() {
        update_catch();
        Log.d("myLog", "enemy catching...");
        return life.is_game_over();
    }

    private void update_catch() {
        bord.set_charactor_view(actor , 0);
        start_game();
        life.to_lower();
    }

    private void init_arrows(AppCompatActivity activity) {
        this.actor = new Actor();
        this.score = new Score(0, activity.findViewById(R.id.game_op_1_LBL_score));
        this.enemy = new Enemy();
        this.coin = new Coin(GAME_ROWS , GAME_COLS);
        this.activity = activity;
        this.bord = new Bord(GAME_ROWS, GAME_COLS, new ArrayList<>(Arrays.asList(actor, enemy)));

        this.life = new Life(NUM_OF_LIFE, new ArrayList<>(Arrays.asList(activity.findViewById(R.id.game_op_1_IMG_heart1),
                activity.findViewById(R.id.game_op_1_IMG_heart2),
                activity.findViewById(R.id.game_op_1_IMG_heart3))));

        HashMap<Moving.Direction, ImageView> direction = new HashMap();
        direction.put(Moving.Direction.up, activity.findViewById(R.id.game_op_1_IMG_up));
        direction.put(Moving.Direction.down, activity.findViewById(R.id.game_op_1_IMG_down));
        direction.put(Moving.Direction.left, activity.findViewById(R.id.game_op_1_IMG_left));
        direction.put(Moving.Direction.right, activity.findViewById(R.id.game_op_1_IMG_right));
        this.move = new MovingByArrows(direction, actor);
        this.actor.setMove(move);
        this.enemy.setMove(move);
    }

    private void init_sensors(AppCompatActivity activity) {
        this.actor = new Actor();
        this.score = new Score(0, activity.findViewById(R.id.game_op_2_LBL_score));
        this.enemy = new Enemy();
        this.coin = new Coin(GAME_ROWS , GAME_COLS);
        this.activity = activity;
        this.bord = new Bord(GAME_ROWS, GAME_COLS, new ArrayList<>(Arrays.asList(actor, enemy)));

        this.life = new Life(NUM_OF_LIFE, new ArrayList<>(Arrays.asList(activity.findViewById(R.id.game_op_2_IMG_heart1),
                activity.findViewById(R.id.game_op_2_IMG_heart2),
                activity.findViewById(R.id.game_op_2_IMG_heart3))));

        this.move = new MovingBySensors(actor);
        this.actor.setMove(move);
        this.enemy.setMove(move);
    }

    public Integer getScore() {
        return this.score.getScore();
    }
}
