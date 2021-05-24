package com.cricketexchange.project.ui.home.live;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class LiveMatches extends Fragment {
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_live_matches, container, false);
        recyclerView=view.findViewById(R.id.liveMatches);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MatchesAdapter adapter=new MatchesAdapter(getContext(),getData(),getChildData());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<MatchesModel> getData() {
        List<MatchesModel> matchesModelList=new ArrayList<>();
        MatchesModel model=new MatchesModel("2 april, Monday");
        matchesModelList.add(model);
        MatchesModel model2=new MatchesModel("8 October, Sunday");
        matchesModelList.add(model2);
        MatchesModel model3=new MatchesModel("27 Jun, Thursday");
        matchesModelList.add(model3);
        return matchesModelList;
    }

    private List<MatchesChildModel> getChildData(){
        List<MatchesChildModel> childModelList=new ArrayList<>();
        MatchesChildModel matchesChildModel=new MatchesChildModel("CSK","MI","Indian Premiure League","MI","MI won by 4wkts","INPROGRESS","1","","","176-5","150-7","19.5","20.0");
        childModelList.add(matchesChildModel);
        MatchesChildModel matchesChildModel2=new MatchesChildModel("DC","MI","Indian Premiure League","DC","DC won by 30 runs","INPROGRESS","12","","","126-2","180-9","7.5","20.0");
        childModelList.add(matchesChildModel2);
        MatchesChildModel matchesChildModel3=new MatchesChildModel("DC","MI","Indian Premiure League","DC","DC won by 30 runs","INPROGRESS","12","","","126-2","180-9","7.5","20.0");
        childModelList.add(matchesChildModel3);
        return childModelList;
    }

}