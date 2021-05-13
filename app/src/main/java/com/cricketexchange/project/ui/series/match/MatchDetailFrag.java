package com.cricketexchange.project.ui.series.match;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class MatchDetailFrag extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<MatchesModel> adapterList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_match_detail, container, false);
        recyclerView=view.findViewById(R.id.matchDetailRv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterList=getData();
        adapter=new MatchesAdapter(adapterList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<MatchesModel> getData() {
        List<MatchesModel> data=new ArrayList<>();
        List<String> list=new ArrayList<>();
        list.add("1 April, Saturday");
        list.add("2 May, Thursday");
        MatchesModel matchesModel=new MatchesModel();
        matchesModel.setDate(list.get(0));
        data.add(matchesModel);
        MatchesModel matchesModel2=new MatchesModel();
        matchesModel2.setDate(list.get(1));
        data.add(matchesModel2);
        return data;
    }
}