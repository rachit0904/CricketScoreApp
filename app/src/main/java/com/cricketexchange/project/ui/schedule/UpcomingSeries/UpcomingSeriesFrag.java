package com.cricketexchange.project.ui.schedule.UpcomingSeries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketexchange.project.Adapter.Recyclerview.UpcomingSeriesAdapter;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.Models.UpcomingSeriesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingSeriesFrag extends Fragment {
    RecyclerView recyclerView;
    List<UpcomingSeriesModel> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_upcoming_series, container, false);
        recyclerView=view.findViewById(R.id.upcomingSeriesRv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list=getData();
        UpcomingSeriesAdapter adapter=new UpcomingSeriesAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<UpcomingSeriesModel> getData() {
        List<UpcomingSeriesModel> modelList=new ArrayList<>();
        UpcomingSeriesModel matchesModel=new UpcomingSeriesModel("May 2021");
        modelList.add(matchesModel);
        UpcomingSeriesModel matchesModel2=new UpcomingSeriesModel("Jan 2022");
        modelList.add(matchesModel2);
        UpcomingSeriesModel matchesModel3=new UpcomingSeriesModel("April 2022");
        modelList.add(matchesModel3);
        return modelList;
    }

}