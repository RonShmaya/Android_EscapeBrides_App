package com.example.escapebrides.game_components;


import android.widget.ImageView;

import com.example.escapebrides.tools.DataManager;

import java.util.ArrayList;
import java.util.List;

public class Bord {
    private int rows;
    private int cols;
    private int actor_start_row_position;
    private int enemy_start_row_position;
    private int actor_and_enemy_start_col_position;
    private List<List<ImageView>> places; //matrix using smart array
    private List<Character> characters;

    public Bord() {

    }
    public Bord(int row, int col , ArrayList<Character> characters) {
        this.rows = row;
        this.cols = col;
        this.characters = new ArrayList<>(characters);
        create_board();
    }
    private void create_board() {
        actor_start_row_position = rows - 1;
        enemy_start_row_position = 0;
        actor_and_enemy_start_col_position = cols / 2;
        places = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            places.add(new ArrayList<>());
            for (int col = 0; col < rows; col++) {
                places.get(row).add(DataManager.getDataManager().get_position_source(row,col)); //get board
            }
        }
    }

    public void start_game_positions(Actor actor, Enemy enemy) {
        places.get(actor_start_row_position).get(actor_and_enemy_start_col_position).setImageResource(actor.getCode());
        places.get(enemy_start_row_position).get(actor_and_enemy_start_col_position).setImageResource(enemy.getCode());
        actor
                .setRow(actor_start_row_position)
                .setCol(actor_and_enemy_start_col_position);
        enemy
                .setRow(enemy_start_row_position)
                .setCol(actor_and_enemy_start_col_position);
    }

    public boolean is_catching(Character char1, Character char2) {
        return char1.catching(char2);

    }

    public void next_character(Character character) {
        if (character.getDirection() != Moving.Direction.None && character.next_step(rows, cols))
            set_character_movement_on_bord(character);
    }
    public void set_character_movement_on_bord(Character character){
        if(!index_taken_by_indexes(character.getLast_row(),character.getLast_col()) && verify_Index_ok(character.getLast_row() , character.getLast_col())){
            places.get(character.getLast_row()).get(character.getLast_col()).setImageResource(DataManager.EMPTY_RESOURCE);
        }
        places.get(character.getRow()).get(character.getCol()).setImageResource(character.getCode());

    }


    public boolean index_taken_by_indexes(int row , int col) {
        Character tmp = new Character().setRow(row).setCol(col);
        return index_taken_by_Object(tmp);
    }

    private boolean index_taken_by_Object(Character other) {
        for (Character chr: characters) {
            if(chr.is_the_same_place(other)){
                return true;
            }
        }
        return false;
    }

    private boolean verify_Index_ok(int characterRow, int characterCol) {
        return characterRow >= 0 && characterRow < rows && characterCol >= 0 && characterCol < cols;
    }

    public void set_charactor_view(Character character , int resId) {
        places.get(character.getRow()).get(character.getCol()).setImageResource(resId);
    }
}
