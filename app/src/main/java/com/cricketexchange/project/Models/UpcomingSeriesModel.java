package com.cricketexchange.project.Models;

public class UpcomingSeriesModel {
    String matchDate;

    public UpcomingSeriesModel() {
    }

    public UpcomingSeriesModel(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }
}
