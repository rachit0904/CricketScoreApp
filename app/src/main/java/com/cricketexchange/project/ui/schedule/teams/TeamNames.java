package com.cricketexchange.project.ui.schedule.teams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class TeamNames extends Fragment {
    RecyclerView recyclerView;
    SearchView searchView;
    RecyclerView.Adapter adapter;
    List<SquadModel> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_team_names, container, false);
        recyclerView=view.findViewById(R.id.teams);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}