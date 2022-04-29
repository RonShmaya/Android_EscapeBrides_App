package com.example.escapebrides.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.escapebrides.R;
import com.example.escapebrides.callBacks.CallBack_location;
import com.example.escapebrides.game_components.PlayerDetails;
import com.example.escapebrides.game_components.TopDetails;
import com.example.escapebrides.tools.DataManager;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class Fragment_Details extends Fragment {
    private final int NUM_OF_PLAYERS = 10;
    private ArrayList<MaterialTextView> table_LBL_names;
    private ArrayList<MaterialTextView> table_LBL_dates;
    private ArrayList<MaterialTextView> table_LBL_scores;
    private ArrayList<ImageView> table_IMG_shows;
    private TopDetails topDetails;
    private int numOfPlayers;
    private CallBack_location callBack_location;

    public Fragment_Details(CallBack_location callBack_location) {
        table_LBL_names = new ArrayList<>();
        table_LBL_dates = new ArrayList<>();
        table_LBL_scores = new ArrayList<>();
        table_IMG_shows = new ArrayList<>();
        this.callBack_location = callBack_location;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__details, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        topDetails = DataManager.getDataManager().get_top_10();
        for (int index = 0; index < topDetails.num_of_top(); index++) {
            table_LBL_names.add((MaterialTextView) get_top_views("name", index ,view, "LBL"));
            table_LBL_dates.add((MaterialTextView) get_top_views("date", index ,view, "LBL"));
            table_LBL_scores.add((MaterialTextView) get_top_views("score", index ,view, "LBL"));
            table_IMG_shows.add((ImageView) get_top_views("show", index ,view, "IMG"));
            init_text(index);

        }
    }

    private void init_text(int index) {
        table_LBL_names.get(index).setText(""+topDetails.getTop_players().get(index).getName());
        table_LBL_dates.get(index).setText(""+topDetails.getTop_players().get(index).getTime());
        table_LBL_scores.get(index).setText(""+topDetails.getTop_players().get(index).getScore());

        table_IMG_shows.get(index).setVisibility(View.VISIBLE);
        table_IMG_shows.get(index).setOnClickListener(view -> {
            send_player_location(index);
        });
    }

    private void send_player_location(int index) {
        if(callBack_location != null) {
            PlayerDetails playerDetails = topDetails.getTop_players().get(index);
            callBack_location.show_on_map(playerDetails.getLat(), playerDetails.getLng() , playerDetails.getName());
        }
    }

    public View get_top_views(String col, int place, View view, String view_type) {
        return view.findViewById(view.getResources().getIdentifier("frage_"+view_type+"_"+col+"_"+place, "id", getActivity().getPackageName() ));
    }

}