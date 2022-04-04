package com.example.escapebrides;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Life {
    private int numOFlives;
    private List<ImageView> hearts;

    public Life() {
    }

    public Life(int numOFlives , ArrayList<ImageView> hearts) {
        this.numOFlives = numOFlives;
        this.hearts = hearts;
    }

    public boolean is_game_over() {
        Log.d("myLog" , "num of life is " + numOFlives);
        return numOFlives <= 0;
    }

    public void to_lower() {
        numOFlives--;
        if(numOFlives>=0){
            hearts.get(numOFlives).setVisibility(View.INVISIBLE);
        }
    }
}
