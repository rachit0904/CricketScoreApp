package com.cricketexchange.project.Models;

public class MatchesModel {
    String date;

    public MatchesModel() {
    }

    public MatchesModel(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
