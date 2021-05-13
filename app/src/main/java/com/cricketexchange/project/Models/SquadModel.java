package com.cricketexchange.project.Models;

import androidx.recyclerview.widget.RecyclerView;

public class SquadModel {
    String squadLogoUrl,squadName;

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
