package com.cricketexchange.project.Models;

public class SessionsDataModel {
    String overStatus,yes,no,op,min,max,overRuns;

    public SessionsDataModel() {
    }

    public SessionsDataModel(String overStatus, String yes, String no, String op, String min, String max, String overRuns) {
        this.overStatus = overStatus;
        this.yes = yes;
        this.no = no;
        this.op = op;
        this.min = min;
        this.max = max;
        this.overRuns = overRuns;
    }

    public String getOverStatus() {
        return overStatus;
    }

    public void setOverStatus(String overStatus) {
        this.overStatus = overStatus;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getOverRuns() {
        return overRuns;
    }

    public void setOverRuns(String overRuns) {
        this.overRuns = overRuns;
    }
}
