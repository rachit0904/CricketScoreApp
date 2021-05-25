package com.cricketexchange.project.Pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.cricketexchange.project.ui.matchdetail.commentary.Commentary;
import com.cricketexchange.project.ui.matchdetail.info.MatchInfo;
import com.cricketexchange.project.ui.matchdetail.live.Live;
import com.cricketexchange.project.ui.matchdetail.scores.TeamScores;
import com.cricketexchange.project.ui.matchdetail.standings.TeamStanding;

public class MatchDetailPager  extends FragmentPagerAdapter {
    final private int noOfTabs;
    public MatchDetailPager(@NonNull FragmentManager fm, int tabs) {
        super(fm);
        this.noOfTabs=tabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Commentary";
            }
            case 1:{
                return "Info";
            } case 2:{
                return "Live";
            } case 3:{
                return "Scores";
            }  case 4:{
                return "Series Table";
            }default:{
                return null;
            }
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new Commentary();
            } case 1:{
                return new MatchInfo();
            }  case 2:{
                return new Live();
            }case 3:{
                return new TeamScores();
            }  case 4:{
                return new TeamStanding();
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
