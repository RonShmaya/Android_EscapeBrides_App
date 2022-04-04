package com.example.escapebrides;

import android.widget.TextView;

public class Score {
    private int score_value = 0;
    private TextView label;

    public Score() {
    }

    public Score(int score_value , TextView label) {
        this.score_value = score_value;
        this.label = label;
    }

    public void add_score(int val) {
        score_value+=val;
        label.setText(""+score_value);
    }
}
