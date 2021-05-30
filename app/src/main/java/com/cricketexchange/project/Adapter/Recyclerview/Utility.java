package com.cricketexchange.project.Adapter.Recyclerview;

public class Utility {
    String arr[] = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};

    public Utility() {

    }

    public String convertstartdate(String date) {
        String mdate = null;
        String mDay = date.split("/")[1];
        String mMonth = date.split("/")[0];
        String mYear = date.split("/")[2];
        mdate = mDay + " " + arr[Integer.parseInt(mMonth)] + " " + mYear;
        return mdate;
    }

}
