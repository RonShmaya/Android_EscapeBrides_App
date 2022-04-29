package com.example.escapebrides.game_components;

import com.example.escapebrides.tools.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Coin extends Character {
    private int row_limit;
    private int col_limit;

    public Coin(int row_limit, int col_limit) {
        super();
        this.row_limit = row_limit;
        this.col_limit = col_limit;
        this.col = -1;
        this.row = -1;
        this.last_col = -1;
        this.last_row = -1;
        this.code = DataManager.getDataManager().get_coin_id();

    }


    public ArrayList<Integer> rand_place() {
        Random rnd = new Random();
        int row = rnd.nextInt(row_limit);
        int col = rnd.nextInt(col_limit);
        return new ArrayList<>(Arrays.asList(row , col));
    }

    @Override
    public Character setDirection(Moving.Direction direction) {
        if (direction != Moving.Direction.None) {
            code = DataManager.getDataManager().get_bride_resource(direction);
        }
        return super.setDirection(direction);
    }//player stack its not change , enemy catch -> disapired

    @Override
    public boolean next_step(int rowBounds, int colBounds) {
        if (!super.next_step(rowBounds, colBounds)) {
            return false;
        }
        if (row >= rowBounds) {
            row = 0;
            col = colBounds / 2;
        }
        return true;
    }

    @Override
    protected boolean is_position_ok(int newRow, int newCol, int rowBounds, int colBounds) {
        return newRow >= 0 && newCol >= 0 && newCol < colBounds;
    }
}
