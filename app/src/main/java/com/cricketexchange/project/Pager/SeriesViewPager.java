package com.cricketexchange.project.Pager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cricketexchange.project.ui.series.match.MatchDetailFrag;
import com.cricketexchange.project.ui.series.pointstable.PointsTableFrag;
import com.cricketexchange.project.ui.series.squad.SquadFrag;

public class SeriesViewPager extends FragmentPagerAdapter {
    final private int noOfTabs;
    final Context mContext;
    public SeriesViewPager(@NonNull FragmentManager fm, int tabs, Context context) {
        super(fm);
        this.noOfTabs=tabs;
        mContext=context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Matches";
            }
            case 1:{
                return "Squad";
            } case 2:{
                return "Points Table";
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
                return new MatchDetailFrag();
            } case 1:{
                return new SquadFrag();
            }  case 2:{
                return new PointsTableFrag();
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
