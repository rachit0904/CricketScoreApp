package com.cricketexchange.project.Models;

import androidx.recyclerview.widget.RecyclerView;

public class SquadModel {
    String squadLogoUrl;
    String squadName;
    String squadFullname;

    public String getSquadFullname() {
        return squadFullname;
    }

    public void setSquadFullname(String squadFullname) {
        this.squadFullname = squadFullname;
    }

    public String getSquadColler() {
        return SquadColler;
    }

    public void setSquadColler(String squadColler) {
        SquadColler = squadColler;
    }

    public SquadModel(String squadLogoUrl, String squadName, String squadFullname, String squadColler) {
        this.squadLogoUrl = squadLogoUrl;
        this.squadName = squadName;
        this.squadFullname = squadFullname;
        SquadColler = squadColler;
    }

    String SquadColler;

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
