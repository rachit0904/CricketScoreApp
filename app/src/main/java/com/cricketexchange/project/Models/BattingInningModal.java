package com.cricketexchange.project.Models;

public class BattingInningModal {
    String playerName,outBy,r,b,four,six,sr;

    public BattingInningModal(String playerName, String outBy, String r, String b, String four, String six, String sr) {
        this.playerName = playerName;
        this.outBy = outBy;
        this.r = r;
        this.b = b;
        this.four = four;
        this.six = six;
        this.sr = sr;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getOutBy() {
        return outBy;
    }

    public void setOutBy(String outBy) {
        this.outBy = outBy;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
    }

    public String getSix() {
        return six;
    }

    public void setSix(String six) {
        this.six = six;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }
}
