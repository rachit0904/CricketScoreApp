package com.cricketexchange.project.Models;

public class NewsModel {
    public NewsModel() {
    }

    public NewsModel(String id, String maintitle, String secondarytitle, String time, String posterurl) {
        this.id = id;
        this.maintitle = maintitle;
        this.secondarytitle = secondarytitle;
        this.time = time;
        this.posterurl = posterurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getSecondarytitle() {
        return secondarytitle;
    }

    public void setSecondarytitle(String secondarytitle) {
        this.secondarytitle = secondarytitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosterurl() {
        return posterurl;
    }

    public void setPosterurl(String posterurl) {
        this.posterurl = posterurl;
    }

    String id, maintitle, secondarytitle, time, posterurl;
}
