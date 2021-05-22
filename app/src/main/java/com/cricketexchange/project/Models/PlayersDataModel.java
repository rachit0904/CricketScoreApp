package com.cricketexchange.project.Models;

public class PlayersDataModel {
    String name,logoUrl,playerType,battingStyle,bowlingStyle;

    public PlayersDataModel() {
    }

    public PlayersDataModel(String name, String logoUrl, String playerType, String battingStyle, String bowlingStyle) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.playerType = playerType;
        this.battingStyle = battingStyle;
        this.bowlingStyle = bowlingStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }
}
