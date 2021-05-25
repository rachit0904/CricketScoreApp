package com.cricketexchange.project.ui.schedule.teams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.cricketexchange.project.Adapter.Recyclerview.TeamRecycleAdapter;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class TeamNames extends Fragment {
    SearchView searchView;
//    RecyclerView recyclerView;
//    RecyclerView.Adapter adapter;
//    List<SquadModel> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_team_names, container, false);
//        recyclerView=view.findViewById(R.id.teams);
        searchView=view.findViewById(R.id.searchTeam);
//        recyclerView.hasFixedSize();
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        list=getData();
//        adapter=new TeamRecycleAdapter(list);
//        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<SquadModel> getData() {
        List<SquadModel> modelList=new ArrayList<>();
        SquadModel model=new SquadModel("","MI");
        modelList.add(model);
        SquadModel model2=new SquadModel("","CSK");
        modelList.add(model2);
        return modelList;
    }
}