package com.cricketexchange.project.ui.matchdetail.standings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketexchange.project.Adapter.Recyclerview.ScoreCardAdapter;
import com.cricketexchange.project.Models.ScoreCardModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class TeamStanding extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ScoreCardModel> scoreCardModelList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_team_standing, container, false);
        recyclerView=view.findViewById(R.id.pointsTableRv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scoreCardModelList.clear();
        scoreCardModelList=getData();
        adapter=new ScoreCardAdapter(getContext(),scoreCardModelList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<ScoreCardModel> getData() {
        List<ScoreCardModel> modelList=new ArrayList<>();
        ScoreCardModel model1=new ScoreCardModel("1","","DC",
                "3","2","1",
                "2","9","+0.632");
        modelList.add(model1);
        ScoreCardModel mode2=new ScoreCardModel("2","","MI",
                "3","1","2",
                "0","7","-0.312");
        modelList.add(mode2);
        ScoreCardModel mode3=new ScoreCardModel("3","","RR",
                "3","1","2",
                "0","7","-0.312");
        modelList.add(mode3);
        ScoreCardModel model4=new ScoreCardModel("4","","CSK",
                "3","2","1",
                "2","9","+0.632");
        modelList.add(model4);
        ScoreCardModel mode5=new ScoreCardModel("5","","SRH",
                "3","1","2",
                "0","7","-0.312");
        modelList.add(mode5);
        ScoreCardModel model6=new ScoreCardModel("6","","RCB",
                "3","2","1",
                "2","9","+0.632");
        modelList.add(model6);
        ScoreCardModel mode7=new ScoreCardModel("7","","KKR",
                "3","1","2",
                "0","7","-0.312");
        modelList.add(mode7);
        ScoreCardModel model8=new ScoreCardModel("8","","PK",
                "3","2","1",
                "2","9","+0.632");
        modelList.add(model8);
        return modelList;
    }

}