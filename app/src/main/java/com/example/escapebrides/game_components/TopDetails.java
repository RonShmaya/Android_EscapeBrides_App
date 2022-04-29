package com.example.escapebrides.game_components;

import java.util.ArrayList;
import java.util.Comparator;

public class TopDetails {
    private final int MAX_TOP = 10;
    private ArrayList<PlayerDetails> top_players;

    public TopDetails(ArrayList<PlayerDetails> top_players) {
        this.top_players = top_players;
    }

    public TopDetails() {  top_players = new ArrayList<>(); }

    public void update_top(PlayerDetails playerDetails){
        top_players.add(playerDetails);
        top_players.sort((pl1, pl2) -> pl2.getScore() - pl1.getScore());
        if (top_players.size() >= MAX_TOP)
            for (int index = MAX_TOP; index < top_players.size() ; index++)
                top_players.remove(index);
    }

    public ArrayList<PlayerDetails> getTop_players() {
        return top_players;
    }
    public int num_of_top(){ return top_players.size();}


}
