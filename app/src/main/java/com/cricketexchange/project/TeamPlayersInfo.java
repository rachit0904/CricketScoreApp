package com.cricketexchange.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamPlayersInfo extends Fragment {
    ImageView logo;
    TextView shortName,fullName;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_team_players_info, container, false);
        logo=view.findViewById(R.id.logo);
        shortName=view.findViewById(R.id.teamShortName);
        fullName=view.findViewById(R.id.teamFullName);
        recyclerView=view.findViewById(R.id.players);
        return view;
    }
}