package com.cricketexchange.project.Pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cricketexchange.project.ui.home.finished.CompletedMatches;
import com.cricketexchange.project.ui.home.live.LiveMatches;
import com.cricketexchange.project.ui.home.upcomingmatches.UpcomingMatches;

public class HomePager extends FragmentPagerAdapter {
    final private int noOfTabs;
    public HomePager(@NonNull FragmentManager fm, int tabs) {
        super(fm);
        this.noOfTabs=tabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Live";
            }
            case 1:{
                return "Upcoming";
            } case 2:{
                return "Finished";
            } default:{
                return null;
            }
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new LiveMatches();
            } case 1:{
                return new UpcomingMatches();
            }  case 2:{
                return new CompletedMatches();
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}