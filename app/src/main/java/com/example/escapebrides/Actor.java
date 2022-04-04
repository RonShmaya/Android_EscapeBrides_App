package com.example.escapebrides;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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
            code = new DataManager().get_groom_resource(direction);
        }
        return super.setDirection(direction);
    }
}
