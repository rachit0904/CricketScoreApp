package com.cricketexchange.project.Models;

public class battingCardModal {
    String playerName,runs,overs;

    public battingCardModal(String playerName, String runs, String overs) {
        this.playerName = playerName;
        this.runs = runs;
        this.overs = overs;
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
