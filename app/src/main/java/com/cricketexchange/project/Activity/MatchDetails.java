package com.cricketexchange.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketexchange.project.Pager.MatchDetailPager;
import com.cricketexchange.project.R;
import com.google.android.material.tabs.TabLayout;

public class MatchDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView team1,team2,back;
    TextView t1Name,t2Name,t1Score,t2Score,t1Overs,t2Overs,matchTitle,cms;
    TabLayout tabLayout;
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        initializeIds();
        back.setOnClickListener(this);
        MatchDetailPager matchDetailPager=new MatchDetailPager(getSupportFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(matchDetailPager);
        tabLayout.setupWithViewPager(pager);
        tabLayout.selectTab(tabLayout.getTabAt(2));
        pager.setCurrentItem(2);
    }

    private void initializeIds() {
        back=findViewById(R.id.bckBtn);
        team1=findViewById(R.id.t1icon);
        team2=findViewById(R.id.t2icon);
        t1Name=findViewById(R.id.t1Name);
        t2Name=findViewById(R.id.t2Name);
        t1Score=findViewById(R.id.t1Score);
        t1Overs=findViewById(R.id.t1Overs);
        t2Score=findViewById(R.id.t2Score);
        t2Overs=findViewById(R.id.t2Overs);
        matchTitle=findViewById(R.id.matchTitle);
        cms=findViewById(R.id.cms);
        tabLayout=findViewById(R.id.tabLayout4);
        pager=findViewById(R.id.matchDetailPager);
    }

    @Override
    public void onClick(View v) {
        if(v==back){
            finish();
        }
    }
}