package com.cricketexchange.project.Pager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cricketexchange.project.ui.schedule.days.DaysFrag;
import com.cricketexchange.project.ui.schedule.series.UpcomingSeriesFrag;
import com.cricketexchange.project.ui.schedule.teams.TeamNames;

public class ScheduleViewPager extends FragmentPagerAdapter {
    final private int noOfTabs;
    final Context mContext;
    public ScheduleViewPager(@NonNull FragmentManager fm, int tabs, Context context) {
        super(fm);
        this.noOfTabs=tabs;
        mContext=context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Upcoming Series";
            }
            case 1:{
                return "Days";
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
                return new TeamNames();
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
