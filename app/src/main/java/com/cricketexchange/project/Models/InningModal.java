package com.cricketexchange.project.Models;

import java.util.List;

public class InningModal {
    String inningName;
    String totalScore;
    List<BattingInningModal> battingInningModalList,bowlingInningModalList;
    List<WicketsFallModel> wicketsFallModelList;

    public InningModal() {
    }

    public InningModal(String inningName, String totalScore, List<BattingInningModal> battingInningModalList, List<BattingInningModal> bowlingInningModalList, List<WicketsFallModel> wicketsFallModelList) {
        this.inningName = inningName;
        this.totalScore = totalScore;
        this.battingInningModalList = battingInningModalList;
        this.bowlingInningModalList = bowlingInningModalList;
        this.wicketsFallModelList = wicketsFallModelList;
    }

    public String getInningName() {
        return inningName;
    }

    public void setInningName(String inningName) {
        this.inningName = inningName;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public List<BattingInningModal> getBattingInningModalList() {
        return battingInningModalList;
    }

    public void setBattingInningModalList(List<BattingInningModal> battingInningModalList) {
        this.battingInningModalList = battingInningModalList;
    }

    public List<BattingInningModal> getBowlingInningModalList() {
        return bowlingInningModalList;
    }

    public void setBowlingInningModalList(List<BattingInningModal> bowlingInningModalList) {
        this.bowlingInningModalList = bowlingInningModalList;
    }

    public List<WicketsFallModel> getWicketsFallModelList() {
        return wicketsFallModelList;
    }

    public void setWicketsFallModelList(List<WicketsFallModel> wicketsFallModelList) {
        this.wicketsFallModelList = wicketsFallModelList;
    }
}
