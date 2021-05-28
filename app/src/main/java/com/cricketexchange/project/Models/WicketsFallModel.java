package com.cricketexchange.project.Models;

public class WicketsFallModel {
    String batsmanName,score,over;

    public WicketsFallModel(String batsmanName, String score, String over) {
        this.batsmanName = batsmanName;
        this.score = score;
        this.over = over;
    }

    public String getBatsmanName() {
        return batsmanName;
    }

    public void setBatsmanName(String batsmanName) {
        this.batsmanName = batsmanName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }
}
