package com.example.escapebrides.game_components;

import android.widget.ImageView;

import com.example.escapebrides.tools.DataManager;

import java.util.HashMap;

public class Actor extends Character {

    private HashMap<Moving.Direction, ImageView> arrows;

    public Actor( Moving move ) {
        super(move);
    }

    public Actor() {
    }

    @Override
    public Character setDirection(Moving.Direction direction) {
        if(direction != Moving.Direction.None) {
            code = DataManager.getDataManager().get_groom_resource(direction);
        }
        return super.setDirection(direction);
    }
}
