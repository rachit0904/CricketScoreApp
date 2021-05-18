package com.cricketexchange.project.Models;

public class SeriesModel {

    String seriesName;
    String duration;
    public SeriesModel() {
    }

    public SeriesModel(String seriesName, String duration) {
        this.seriesName = seriesName;
        this.duration = duration;
    }

    public SeriesModel(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
