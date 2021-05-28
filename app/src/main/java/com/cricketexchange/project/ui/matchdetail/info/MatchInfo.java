package com.cricketexchange.project.ui.matchdetail.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cricketexchange.project.Activity.SeriesDetail;
import com.cricketexchange.project.Activity.TeamPlayersInfo;
import com.cricketexchange.project.R;

public class MatchInfo extends Fragment implements View.OnClickListener {
    TextView tossResult,seriesName,matchType,startDateTime,venue,pt1SName,pt1FName,pt2SName,pt2FName,primaryUmpire,thirdUmpire,refree;
    CardView seriesCard;
    ImageView t1Logo,t2Logo;
    RelativeLayout t1Layout,t2Layout;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_match_info, container, false);
        initialize();
        setData();
        seriesCard.setOnClickListener(this);
        t1Layout.setOnClickListener(this);
        t2Layout.setOnClickListener(this);
        return view;
    }

    private void setData() {
    }

    private void initialize() {
        tossResult=view.findViewById(R.id.tossStatus);
        seriesName=view.findViewById(R.id.tounamentName);seriesCard=view.findViewById(R.id.showSeriesDetail);
        matchType=view.findViewById(R.id.seriesType);venue=view.findViewById(R.id.venue);
        startDateTime=view.findViewById(R.id.startTime);
        pt1SName=view.findViewById(R.id.t1shortName);pt1FName=view.findViewById(R.id.t1fullName);
        t1Layout=view.findViewById(R.id.teamcard);t1Logo=view.findViewById(R.id.pt1Logo);
        pt2SName=view.findViewById(R.id.t2shortName);pt2FName=view.findViewById(R.id.t2fullName);
        t2Layout=view.findViewById(R.id.teamcard2);t2Logo=view.findViewById(R.id.pt2Logo);
        primaryUmpire=view.findViewById(R.id.umpires);
        thirdUmpire=view.findViewById(R.id.thirdUmpire);
        refree=view.findViewById(R.id.refree);
    }

    @Override
    public void onClick(View v) {
        if(v==seriesCard){
            //pass intent to series Detail page
            Intent intent=new Intent(getContext(), SeriesDetail.class);
            startActivity(intent);
        }
        if(v==t1Layout){
            //pass intent to team info page
            Intent intent=new Intent(getContext(), TeamPlayersInfo.class);
            startActivity(intent);
        }
        if(v==t2Layout){
            //pass intent to team info page
            Intent intent=new Intent(getContext(), TeamPlayersInfo.class);
            startActivity(intent);
        }
    }

}