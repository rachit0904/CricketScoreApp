package com.cricketexchange.project.ui.matchdetail.live;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketexchange.project.Activity.MatchDetails;
import com.cricketexchange.project.Adapter.Recyclerview.CurrentInningBattingAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.PartnershipsAdapter;
import com.cricketexchange.project.Models.PartnershipsModal;
import com.cricketexchange.project.Models.battingCardModal;
import com.cricketexchange.project.Pager.MatchDetailPager;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.matchdetail.scores.TeamScores;

import java.util.ArrayList;
import java.util.List;

public class Live extends Fragment implements View.OnClickListener {
    TextView currentBatsman1,currentBatsman2,currentBatsman1Score,currentBatsman2Score,currentBowler,currentBowlerScore,RRR,CRR;
    TextView currentInningTeamName,currentInningTeamScore;
    ImageView currentInningTeamLogo;
    RecyclerView inningBattingRv,inningYetToBat,inningPartnerShips;
    CardView showScoreCard;
    View view;
    List<battingCardModal> battingCardModalList=new ArrayList<>();
    List<battingCardModal> yetToBatList=new ArrayList<>();
    List<PartnershipsModal> partnershipsModalList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_live, container, false);
        initialize();
        setData();
        showScoreCard.setOnClickListener(this);
        return view;
    }

    private void setData() {
        //batsman1 batsman2 bowler name & scores
        //CRR RRR
        //Current inning team name,score and logo

        //RVs
        //current inning battings
        inningBattingRv.hasFixedSize();
        inningBattingRv.setLayoutManager(new LinearLayoutManager(getContext()));
        CurrentInningBattingAdapter adapter=new CurrentInningBattingAdapter(getContext(),setCurrentBattingInningData());
        inningBattingRv.setAdapter(adapter);
        //yet to bat RV
        inningYetToBat.hasFixedSize();
        inningYetToBat.setLayoutManager(new LinearLayoutManager(getContext()));
        CurrentInningBattingAdapter adapter2=new CurrentInningBattingAdapter(getContext(),setYetToBatData());
        inningYetToBat.setAdapter(adapter2);
        //partnerships data Rv
        inningPartnerShips.hasFixedSize();
        inningPartnerShips.setLayoutManager(new LinearLayoutManager(getContext()));
        PartnershipsAdapter adapter3=new PartnershipsAdapter(getContext(),setPartnershipsData());
        inningPartnerShips.setAdapter(adapter3);

    }

    private List<battingCardModal> setCurrentBattingInningData(){
        battingCardModalList.clear();
        battingCardModal modal=new battingCardModal("Dwyane Bravo","74","32","","");
        battingCardModalList.add(modal);
        battingCardModal modal2=new battingCardModal("MS Dhoni","74","32","","");
        battingCardModalList.add(modal2);
        return battingCardModalList;
    }

    private List<battingCardModal> setYetToBatData(){
        yetToBatList.clear();
        battingCardModal modal=new battingCardModal("Ravindra Jadeja","","","","");
        yetToBatList.add(modal);
        battingCardModal modal2=new battingCardModal("Suresh Raina","","","","");
        yetToBatList.add(modal2);
        return yetToBatList;
    }

    private List<PartnershipsModal> setPartnershipsData(){
        partnershipsModalList.clear();
        PartnershipsModal modal=new PartnershipsModal("MS Dhoni","Suresh Raina","74","11","32","8");
        partnershipsModalList.add(modal);
        return partnershipsModalList;
    }

    private void initialize(){
        currentBatsman1=view.findViewById(R.id.currentBatsman1);
        currentBatsman1Score=view.findViewById(R.id.currentBatsmanScore);
        currentBatsman2=view.findViewById(R.id.currentBatsman2);
        currentBatsman2Score=view.findViewById(R.id.currentBatsman2Score);
        currentBowler=view.findViewById(R.id.currentBowler);
        currentBowlerScore=view.findViewById(R.id.currentBowlerScore);
        RRR=view.findViewById(R.id.rrr);
        CRR=view.findViewById(R.id.crr);
        currentInningTeamName=view.findViewById(R.id.currentTeamName);
        currentInningTeamScore=view.findViewById(R.id.currentTeamScore);
        currentInningTeamLogo=view.findViewById(R.id.currentTeamLogo);
        inningBattingRv=view.findViewById(R.id.currentInningRv);
        showScoreCard=view.findViewById(R.id.moreScores);
        inningYetToBat=view.findViewById(R.id.upcomingBatting);
        inningPartnerShips=view.findViewById(R.id.partnerships);

    }

    @Override
    public void onClick(View v) {
        if(v==showScoreCard){
        }
    }
}