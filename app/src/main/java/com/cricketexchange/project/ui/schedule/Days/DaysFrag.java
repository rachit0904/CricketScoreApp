package com.cricketexchange.project.ui.schedule.Days;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class DaysFrag extends Fragment {
    RecyclerView recyclerView;
    List<MatchesModel> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_days, container, false);
        recyclerView=view.findViewById(R.id.days);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list=getData();
        MatchesAdapter adapter=new MatchesAdapter(list);
        recyclerView.setAdapter(adapter);
        return view;
    }


    private List<MatchesModel> getData() {
        List<MatchesModel> matchesModelList=new ArrayList<>();
        MatchesModel model=new MatchesModel("2 april, Monday");
        matchesModelList.add(model);
        MatchesModel model2=new MatchesModel("8 October, Sunday");
        matchesModelList.add(model2);
        return matchesModelList;
    }

}