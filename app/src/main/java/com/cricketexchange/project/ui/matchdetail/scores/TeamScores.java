package com.cricketexchange.project.ui.matchdetail.scores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricketexchange.project.Adapter.Recyclerview.InningBattingBowlingAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.WicketsAdapter;
import com.cricketexchange.project.Models.BattingInningModal;
import com.cricketexchange.project.Models.WicketsFallModel;
import com.cricketexchange.project.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TeamScores extends Fragment {
    TabLayout inningsTab;
    TextView totalScore;
    RecyclerView battingRv;
    RecyclerView bowlingRv;
    RecyclerView wicketsRv;
    View view;
    List<BattingInningModal> battingInningModalList=new ArrayList<>();
    List<BattingInningModal> bowlingInningModalList=new ArrayList<>();
    List<WicketsFallModel> wicketsFallModelList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_team_scores, container, false);
        initialize();
        //get current inning id
        int id=1;
        //if inning id=3 -> super over
        if(id>2) {
            inningsTab.addTab(inningsTab.newTab().setText("Super Over"));
        }
        //set values default for current inn
        inningData(id-1);
        inningsTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                inningData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void inningData(int id) {
        //set current team's score
        //get RVs data for inning-id
        battingRv.hasFixedSize();
        battingRv.setLayoutManager(new LinearLayoutManager(getContext()));
        InningBattingBowlingAdapter adapter=new InningBattingBowlingAdapter(getContext(),getBattingData(id));
        battingRv.setAdapter(adapter);
        bowlingRv.hasFixedSize();
        bowlingRv.setLayoutManager(new LinearLayoutManager(getContext()));
        InningBattingBowlingAdapter adapter2=new InningBattingBowlingAdapter(getContext(),getBowlingData(id));
        bowlingRv.setAdapter(adapter2);
        wicketsRv.hasFixedSize();
        wicketsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        WicketsAdapter wicketsAdapter=new WicketsAdapter(getContext(),getWktFallData(id));
        wicketsRv.setAdapter(wicketsAdapter);
    }

    private List<WicketsFallModel> getWktFallData(int id) {
        //for dynamic data use id
        wicketsFallModelList.clear();
        WicketsFallModel model=new WicketsFallModel("Salvan Browne","1-4","11.4");
        wicketsFallModelList.add(model);
        return wicketsFallModelList;
    }

    private List<BattingInningModal> getBowlingData(int id) {
        //for dynamic data use id
        bowlingInningModalList.clear();
        //For Bowler pass ->(Bowler Name,runs,wkt,wide,noBall,economy);
        BattingInningModal modal=new BattingInningModal("AB de Villers","","102","32","3","2","18.8");
        bowlingInningModalList.add(modal);
        return bowlingInningModalList;
    }

    private List<BattingInningModal> getBattingData(int id) {
        //for dynamic data use id
        battingInningModalList.clear();
        BattingInningModal modal=new BattingInningModal("Quinton de Knock","not out","2","5","2","3","205.6");
        battingInningModalList.add(modal);
        return battingInningModalList;
    }


    public void initialize() {
        inningsTab=view.findViewById(R.id.inningTab);
        totalScore=view.findViewById(R.id.totalScore);
        battingRv=view.findViewById(R.id.inningBatingRv);
        bowlingRv=view.findViewById(R.id.inningBowlingRv);
        wicketsRv=view.findViewById(R.id.wicketsRv);
    }
}