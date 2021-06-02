package com.cricketexchange.project.Models;

public class MatchesChildModel {
    String team1, team2, premiure, winTeamName, matchSummery, status, startTime, startDate, team1Url, Team2Url, team1score, team2score, team1over, team2over, isDraw, t1iIsBatting, t2IsBatting, mId, sId;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MatchesChildModel(String team1, String team2, String premiure, String winTeamName, String matchSummery, String status, String startTime, String startDate, String team1Url, String team2Url, String team1score, String team2score, String team1over, String team2over, String isDraw, String t1iIsBatting, String t2IsBatting, String mId, String sId, String name, String type, String iswomen, String ismultiday) {
        this.team1 = team1;
        this.team2 = team2;
        this.premiure = premiure;
        this.winTeamName = winTeamName;
        this.matchSummery = matchSummery;
        this.status = status;
        this.startTime = startTime;
        this.startDate = startDate;
        this.team1Url = team1Url;
        Team2Url = team2Url;
        this.team1score = team1score;
        this.team2score = team2score;
        this.team1over = team1over;
        this.team2over = team2over;
        this.isDraw = isDraw;
        this.t1iIsBatting = t1iIsBatting;
        this.t2IsBatting = t2IsBatting;
        this.mId = mId;
        this.sId = sId;
        this.name = name;
        this.type = type;
        this.iswomen = iswomen;
        this.ismultiday = ismultiday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIswomen() {
        return iswomen;
    }

    public void setIswomen(String iswomen) {
        this.iswomen = iswomen;
    }

    public String getIsmultiday() {
        return ismultiday;
    }

    public void setIsmultiday(String ismultiday) {
        this.ismultiday = ismultiday;
    }

    public MatchesChildModel(String team1, String team2, String premiure, String winTeamName, String matchSummery, String status, String startTime, String startDate, String team1Url, String team2Url, String team1score, String team2score, String team1over, String team2over, String isDraw, String t1iIsBatting, String t2IsBatting, String mId, String sId, String type, String iswomen, String ismultiday) {
        this.team1 = team1;
        this.team2 = team2;
        this.premiure = premiure;
        this.winTeamName = winTeamName;
        this.matchSummery = matchSummery;
        this.status = status;
        this.startTime = startTime;
        this.startDate = startDate;
        this.team1Url = team1Url;
        Team2Url = team2Url;
        this.team1score = team1score;
        this.team2score = team2score;
        this.team1over = team1over;
        this.team2over = team2over;
        this.isDraw = isDraw;
        this.t1iIsBatting = t1iIsBatting;
        this.t2IsBatting = t2IsBatting;
        this.mId = mId;
        this.sId = sId;
        this.type = type;
        this.iswomen = iswomen;
        this.ismultiday = ismultiday;
    }

    String type, iswomen, ismultiday;

    public MatchesChildModel() {
    }

    public MatchesChildModel(String team1, String team2, String premiure, String winTeamName, String matchSummery, String status, String startTime, String team1Url, String team2Url, String team1score, String team2score, String team1over, String team2over) {
        this.team1 = team1;
        this.team2 = team2;
        this.premiure = premiure;
        this.winTeamName = winTeamName;
        this.matchSummery = matchSummery;
        this.status = status;
        this.startTime = startTime;
        this.team1Url = team1Url;
        this.Team2Url = team2Url;
        this.team1score = team1score;
        this.team2score = team2score;
        this.team1over = team1over;
        this.team2over = team2over;
    }

    public String getT1iIsBatting() {
        return t1iIsBatting;
    }

    public void setT1iIsBatting(String t1iIsBatting) {
        this.t1iIsBatting = t1iIsBatting;
    }

    public String getT2IsBatting() {
        return t2IsBatting;
    }

    public void setT2IsBatting(String t2IsBatting) {
        this.t2IsBatting = t2IsBatting;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(String isDraw) {
        this.isDraw = isDraw;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMatchSummery() {
        return matchSummery;
    }

    public void setMatchSummery(String matchSummery) {
        this.matchSummery = matchSummery;
    }

    public String getTeam1score() {
        return team1score;
    }

    public void setTeam1score(String team1score) {
        this.team1score = team1score;
    }

    public String getTeam2score() {
        return team2score;
    }

    public void setTeam2score(String team2score) {
        this.team2score = team2score;
    }

    public String getTeam1over() {
        return team1over;
    }

    public void setTeam1over(String team1over) {
        this.team1over = team1over;
    }

    public String getTeam2over() {
        return team2over;
    }

    public void setTeam2over(String team2over) {
        this.team2over = team2over;
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
