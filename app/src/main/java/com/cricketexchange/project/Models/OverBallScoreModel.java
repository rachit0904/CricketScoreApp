package com.cricketexchange.project.Models;

public class OverBallScoreModel {
    String ballnumber, ballrun;
    String iswicket;

    public OverBallScoreModel(String ballnumber, String ballrun, String iswicket) {
        this.ballnumber = ballnumber;
        this.ballrun = ballrun;
        this.iswicket = iswicket;
    }

    public String getBallnumber() {
        return ballnumber;
    }

    public void setBallnumber(String ballnumber) {
        this.ballnumber = ballnumber;
    }

    public String getBallrun() {
        return ballrun;
    }

    public void setBallrun(String ballrun) {
        this.ballrun = ballrun;
    }

    public String getIswicket() {
        return iswicket;
    }

    public void setIswicket(String iswicket) {
        this.iswicket = iswicket;
    }

    public OverBallScoreModel() {
    }
}
