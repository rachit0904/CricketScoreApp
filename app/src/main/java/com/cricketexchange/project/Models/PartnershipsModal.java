package com.cricketexchange.project.Models;

public class PartnershipsModal {
    String player1,player2,player1Runs,player2Runs,player1Overs,player2Overs,mid,sid;

    public PartnershipsModal(String player1, String player2, String player1Runs, String player2Runs, String player1Overs, String player2Overs) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Runs = player1Runs;
        this.player2Runs = player2Runs;
        this.player1Overs = player1Overs;
        this.player2Overs = player2Overs;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer1Runs() {
        return player1Runs;
    }

    public void setPlayer1Runs(String player1Runs) {
        this.player1Runs = player1Runs;
    }

    public String getPlayer2Runs() {
        return player2Runs;
    }

    public void setPlayer2Runs(String player2Runs) {
        this.player2Runs = player2Runs;
    }

    public String getPlayer1Overs() {
        return player1Overs;
    }

    public void setPlayer1Overs(String player1Overs) {
        this.player1Overs = player1Overs;
    }

    public String getPlayer2Overs() {
        return player2Overs;
    }

    public void setPlayer2Overs(String player2Overs) {
        this.player2Overs = player2Overs;
    }
}
