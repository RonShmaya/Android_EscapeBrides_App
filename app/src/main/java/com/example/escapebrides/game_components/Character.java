package com.example.escapebrides.game_components;

import android.util.Log;

import java.util.ArrayList;

public class Character {

    protected Moving.Direction direction;
    protected Moving move;
    protected int code;
    protected int col;
    protected int row;
    protected int last_col;
    protected int last_row;

    public Character() {}

    public Character(Moving move) {
        this.move = move;
    }

    public boolean catching(Character other){
        return is_the_same_place(other) || is_switch_places(other);
    }
    public boolean next_step(int rowBounds , int colBounds) {
        ArrayList<Integer> next_position = move.next_position(direction);
        int newRow = next_position.get(move.VERTICAL) + row;
        int newCol = next_position.get(move.HORIZONTAL) + col;

        if(is_position_ok(newRow, newCol, rowBounds, colBounds)){
            setRow(newRow);
            setCol(newCol);
           return true;
        }
        return false;
    }

    public boolean is_the_same_place(Character other){
        return row == other.row && col == other.col;
    }
    public boolean is_switch_places(Character other){
        return row == other.last_row && col == other.last_col && last_row == other.row && last_col == other.col;
    }

    protected boolean is_position_ok(int newRow , int newCol, int rowBounds , int colBounds) {
        return newRow >= 0 && newRow < rowBounds && newCol >= 0 && newCol < colBounds;
    }

    // ---------get and set-------------
    public int getRow() {
        return row;
    }

    public Character setRow(int row) {
        setLast_row(this.row);
        this.row = row;
        return this;
    }
    public int getCol() {
        return col;
    }

    public Character setCol(int col) {
        setLast_col(this.col);
        this.col = col;
        return this;
    }

    public Character setMove(Moving move) {
        this.move = move;
        return this;
    }

    public Character setCode(int code) {
        this.code = code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Moving.Direction getDirection() {
        return direction;
    }

    public Character setDirection(Moving.Direction direction) {
        Log.d("myLog" , this.getClass().getSimpleName()+"direction is "+direction.toString());
        this.direction = direction;
        return this;
    }

    public Character setLast_col(int last_col) {
        this.last_col = last_col;
        return this;
    }

    public Character setLast_row(int last_row) {
        this.last_row = last_row;
        return this;
    }

    public int getLast_col() {
        return last_col;
    }

    public int getLast_row() {
        return last_row;
    }
}
