package com.cricketexchange.project.Models;

public class ScoreCardModel {
    String rowCount,teamLogoURL,teamName,playedMatches,wonMatches,lostMatches,nr,pts,nrr;

    public ScoreCardModel() {
    }

    public ScoreCardModel(String rowCount, String teamLogoURL, String teamName, String playedMatches, String wonMatches, String lostMatches, String nr, String pts, String nrr) {
        this.rowCount = rowCount;
        this.teamLogoURL = teamLogoURL;
        this.teamName = teamName;
        this.playedMatches = playedMatches;
        this.wonMatches = wonMatches;
        this.lostMatches = lostMatches;
        this.nr = nr;
        this.pts = pts;
        this.nrr = nrr;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public String getTeamLogoURL() {
        return teamLogoURL;
    }

    public void setTeamLogoURL(String teamLogoURL) {
        this.teamLogoURL = teamLogoURL;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayedMatches() {
        return playedMatches;
    }

    public void setPlayedMatches(String playedMatches) {
        this.playedMatches = playedMatches;
    }

    public String getWonMatches() {
        return wonMatches;
    }

    public void setWonMatches(String wonMatches) {
        this.wonMatches = wonMatches;
    }

    public String getLostMatches() {
        return lostMatches;
    }

    public void setLostMatches(String lostMatches) {
        this.lostMatches = lostMatches;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getPts() {
        return pts;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

    public String getNrr() {
        return nrr;
    }

    public void setNrr(String nrr) {
        this.nrr = nrr;
    }
}
