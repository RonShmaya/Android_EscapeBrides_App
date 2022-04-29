package com.example.escapebrides.game_components;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PlayerDetails {
    private String name;
    private Double lng;
    private Double lat;
    private String time;
    private Integer score;

    public PlayerDetails() {
        time = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.US).format(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLat() {
        return lat;
    }

    public String getTime() {
        return time;
    }

    public Integer getScore() {
        return score;
    }

    public PlayerDetails setName(String name) {
        this.name = name;
        return this;
    }

    public PlayerDetails setLng(Double lng) {
        this.lng = lng;
        return this;
    }

    public PlayerDetails setLat(Double lat) {
        this.lat = lat;
        return this;
    }

    public PlayerDetails setScore(Integer score) {
        this.score = score;
        return this;
    }

}
