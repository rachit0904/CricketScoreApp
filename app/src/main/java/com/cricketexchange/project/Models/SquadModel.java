package com.cricketexchange.project.Models;

import androidx.recyclerview.widget.RecyclerView;

public class SquadModel {
    String squadLogoUrl;
    String squadName;
    String squadFullname;


    public SquadModel(String SquadID, String squadLogoUrl, String squadName, String squadFullname, String squadColler) {
        this.squadLogoUrl = squadLogoUrl;
        this.squadName = squadName;
        this.squadFullname = squadFullname;
        this.SquadColor = squadColler;
        this.SquadID = SquadID;

    }

    String SquadColor, SquadID;

    public SquadModel() {
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

    public String getSquadID() {
        return SquadID;
    }

    public void setSquadID(String squadID) {
        SquadID = squadID;
    }
}
