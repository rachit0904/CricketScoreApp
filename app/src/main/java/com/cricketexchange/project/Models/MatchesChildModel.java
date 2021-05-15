package com.cricketexchange.project.Models;

public class MatchesChildModel {
    String team1,team2,premiure,winTeamName,status,startTime,team1Url,Team2Url;

    public MatchesChildModel() {
    }

    public MatchesChildModel(String team1, String team2, String premiure, String winTeamName, String status, String startTime, String team1Url, String team2Url) {
        this.team1 = team1;
        this.team2 = team2;
        this.premiure = premiure;
        this.winTeamName = winTeamName;
        this.status = status;
        this.startTime = startTime;
        this.team1Url = team1Url;
        Team2Url = team2Url;
    }

    public String getTeam1Url() {
        return team1Url;
    }

    public void setTeam1Url(String team1Url) {
        this.team1Url = team1Url;
    }

    public String getTeam2Url() {
        return Team2Url;
    }

    public void setTeam2Url(String team2Url) {
        Team2Url = team2Url;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getPremiure() {
        return premiure;
    }

    public void setPremiure(String premiure) {
        this.premiure = premiure;
    }

    public String getWinTeamName() {
        return winTeamName;
    }

    public void setWinTeamName(String winTeamName) {
        this.winTeamName = winTeamName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
