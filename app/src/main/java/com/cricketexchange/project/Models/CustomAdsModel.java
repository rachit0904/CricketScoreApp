package com.cricketexchange.project.Models;

public class CustomAdsModel {
    public CustomAdsModel(String name, String url, String number) {
        this.name = name;
        this.url = url;
        this.number = number;
    }

    public CustomAdsModel() {
    }

    String name, url, number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
