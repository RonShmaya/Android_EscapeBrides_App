package com.example.escapebrides.game_components;

import com.example.escapebrides.tools.DataManager;

import java.util.Random;

public class Enemy extends Character{
    public Enemy() {
    }
    public Enemy(Moving move) {
        super(move);
    }


    public void rand_direction() {
        Random rnd = new Random();
        int direction = rnd.nextInt(Moving.Direction.values().length-1);
        setDirection(Moving.Direction.values()[direction]);
    }
    @Override
    public Character setDirection(Moving.Direction direction) {
        if(direction != Moving.Direction.None) {
            code = DataManager.getDataManager().get_bride_resource(direction);
        }
        return super.setDirection(direction);
    }

    @Override
    public boolean next_step(int rowBounds, int colBounds) {
        if(!super.next_step(rowBounds, colBounds)){
            return false;
        }
        if(row >= rowBounds){
            row = 0;
            col =colBounds / 2;
        }
        return true;
    }

    @Override
    protected boolean is_position_ok(int newRow, int newCol, int rowBounds, int colBounds) {
        return newRow >= 0  && newCol >= 0 && newCol < colBounds;
    }
}
