package com.cricketexchange.project.Models;

public class battingCardModal {
    String playerName,runs,overs,sid,mid;

    public battingCardModal(String playerName, String runs, String overs, String sid, String mid) {
        this.playerName = playerName;
        this.runs = runs;
        this.overs = overs;
        this.sid = sid;
        this.mid = mid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }
}
