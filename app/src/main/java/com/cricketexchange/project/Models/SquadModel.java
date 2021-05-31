package com.cricketexchange.project.Models;

public class SquadModel {
    String id;
    String squadName;
    String squadFullname;
    String squadLogoUrl;
    String SquadColor;
    public String getSquadFullname() {
        return squadFullname;
    }

    public void setSquadFullname(String squadFullname) {
        this.squadFullname = squadFullname;
    }

    public String getSquadColor() {
        return SquadColor;
    }

    public void setSquadColor(String squadColor) {
        SquadColor = squadColor;
    }

    public SquadModel(String id, String squadName, String squadFullname, String squadLogoUrl, String squadColor) {
        this.id = id;
        this.squadName = squadName;
        this.squadFullname = squadFullname;
        this.squadLogoUrl = squadLogoUrl;
        SquadColor = squadColor;
    }

    public SquadModel() {
    }

    public SquadModel(String squadLogoUrl, String squadName) {
        this.squadLogoUrl = squadLogoUrl;
        this.squadName = squadName;
    }

    public String getSquadLogoUrl() {
        return squadLogoUrl;
    }

    public void setSquadLogoUrl(String squadLogoUrl) {
        this.squadLogoUrl = squadLogoUrl;
    }

    public String getSquadName() {
        return squadName;
    }

    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }

}
