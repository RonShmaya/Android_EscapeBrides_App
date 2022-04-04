package com.example.escapebrides;

import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class GameManager {
    public final int REGULARE_GAME_ROWS = 5;
    public final int REGULARE_GAME_COLS = 3;
    public final int NUM_OF_LIFE = 3;
    private Life life;
    private Score score;
    private Actor actor;
    private Enemy enemy;
    private Bord bord;
    private Moving move;
    boolean is_enemy_catched = false;
    private AppCompatActivity activity;

    public GameManager() {
    }

    public GameManager(AppCompatActivity activity) {
        init(activity);
    }

    public void start_game() {
        actor
                .setDirection(Moving.Direction.None)
                .setCode(new DataManager().get_groom_resource(Moving.Direction.up));

        enemy
                .setDirection(Moving.Direction.None)
                .setCode(new DataManager().get_bride_resource(Moving.Direction.up));

        bord.start_game_positions(actor, enemy);
    }
    public boolean step() {
        if (is_enemy_catched) {
            is_enemy_catched = false;
            return exec_enemy_catched();
        }
        enemy.rand_direction();//TODO: on Ver2 send coin and verify places + add coin rnd and verify is there isn't enemy
        bord.next_character(enemy);
        bord.next_character(actor);

        if (bord.is_catching(actor, enemy)) {
            exe_boom();
            is_enemy_catched = true;
            return false;
        }
        exec_enemy_not_catching();
        is_enemy_catched = false;
        return false;
    }

    private void exe_boom() { //TODO: on ver two can fit to coin
        bord.set_charactor_view(enemy, enemy.getRow(), enemy.getCol(), 0);
        bord.set_charactor_view(actor, actor.getRow(), actor.getCol(), new DataManager().get_enemy_catch_id());
        new GameServices(activity).makeCatchVibrate();
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
        bord.set_charactor_view(actor, actor.getRow(), actor.getCol(), 0);
        start_game();
        life.to_lower();
    }

    private void init(AppCompatActivity activity) {
        this.actor = new Actor();
        this.score = new Score(0, activity.findViewById(R.id.main_LBL_score));
        this.enemy = new Enemy();
        this.activity = activity;
        this.bord = new Bord(REGULARE_GAME_ROWS, REGULARE_GAME_COLS, new DataManager(activity), new ArrayList<>(Arrays.asList(actor, enemy)));

        this.life = new Life(NUM_OF_LIFE, new ArrayList<>(Arrays.asList(activity.findViewById(R.id.main_IMG_heart1),
                activity.findViewById(R.id.main_IMG_heart2),
                activity.findViewById(R.id.main_IMG_heart3))));

        HashMap<Moving.Direction, ImageView> direction = new HashMap();
        direction.put(Moving.Direction.up, activity.findViewById(R.id.main_IMG_up));
        direction.put(Moving.Direction.down, activity.findViewById(R.id.main_IMG_down));
        direction.put(Moving.Direction.left, activity.findViewById(R.id.main_IMG_left));
        direction.put(Moving.Direction.right, activity.findViewById(R.id.main_IMG_right));
        this.move = new MovingByArrows(direction, actor);
        this.actor.setMove(move);
        this.enemy.setMove(move);
    }

}
