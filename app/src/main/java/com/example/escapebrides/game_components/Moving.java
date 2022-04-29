package com.example.escapebrides.game_components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class Moving {
    public enum Direction {left, right, up, down,None};
    public final int VERTICAL = 0 ;
    public final int HORIZONTAL = 1;
    protected final int UP = -1;
    protected final int Down = 1;
    protected final int LEFT = -1;
    protected final int RIGHT = 1;
    protected final int NONE = 0;
    protected HashMap<Direction , ArrayList<Integer>> moving;


    public Moving() {
        moving = new HashMap<>();
        moving.put(Direction.up , new ArrayList<>(Arrays.asList(UP,NONE)));
        moving.put(Direction.down ,  new ArrayList<>(Arrays.asList(Down,NONE)));
        moving.put(Direction.left , new ArrayList<>(Arrays.asList(NONE,LEFT)));
        moving.put(Direction.right , new ArrayList<>(Arrays.asList(NONE,RIGHT)));
        moving.put(Direction.None , new ArrayList<>(Arrays.asList(NONE,NONE)));
    }

    public  ArrayList<Integer> next_position(Direction direction){
        return moving.get(direction);
    }
    protected abstract void active_callback_moving();
}
