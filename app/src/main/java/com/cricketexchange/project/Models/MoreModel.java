package com.cricketexchange.project.Models;

public class MoreModel {
    String text;
    int mainincon, secondaryicon;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMainincon() {
        return mainincon;
    }

    public void setMainincon(int mainincon) {
        this.mainincon = mainincon;
    }

    public int getSecondaryicon() {
        return secondaryicon;
    }

    public void setSecondaryicon(int secondaryicon) {
        this.secondaryicon = secondaryicon;
    }

    public MoreModel() {
    }

    public MoreModel(int mainincon, String text, int secondaryicon) {
        this.text = text;
        this.mainincon = mainincon;
        this.secondaryicon = secondaryicon;
    }


}
