package com.cricketexchange.project.Pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cricketexchange.project.ui.schedule.Days.DaysFrag;
import com.cricketexchange.project.ui.schedule.UpcomingSeries.UpcomingSeriesFrag;
import com.cricketexchange.project.ui.schedule.teams.teams;

public class ScheduleViewPager extends FragmentPagerAdapter {
    final private int noOfTabs;
    public ScheduleViewPager(@NonNull FragmentManager fm, int tabs) {
        super(fm);
        this.noOfTabs=tabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Series";
            }
            case 1:{
                return "Matches";
            } case 2:{
                return "Teams";
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
                return new UpcomingSeriesFrag();
            } case 1:{
                return new DaysFrag();
            }  case 2:{
                return new teams();
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