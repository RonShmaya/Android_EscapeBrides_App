package com.example.escapebrides;

import android.widget.ImageView;

import java.util.HashMap;

public class MovingByArrows extends Moving{
    HashMap<Direction, ImageView> arrows;
    Character character;

    public MovingByArrows(HashMap<Direction, ImageView> arrows, Character character) {
        super();
        this.arrows = arrows;
        this.character = character;
        active_callback_moving();
    }

    @Override
    protected void active_callback_moving() {
        arrows.forEach((direction, arrow) -> arrow.setOnClickListener(view -> click_direction(direction)));
    }
    public void click_direction(Moving.Direction direction){
        character.setDirection(direction);
    }

}



