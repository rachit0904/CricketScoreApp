package com.cricketexchange.project.ui.series.squad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketexchange.project.Adapter.Recyclerview.SquadParentAdapter;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class SquadFrag extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<SquadModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_squad, container, false);
        recyclerView=view.findViewById(R.id.squadRv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list=getData();
        adapter=new SquadParentAdapter(list);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<SquadModel> getData() {
        List<SquadModel> data=new ArrayList<>();
        SquadModel model=new SquadModel();SquadModel mode2=new SquadModel();SquadModel mode3=new SquadModel();
        model.setSquadName("Chennai Super Kings");
        data.add(model);
        mode2.setSquadName("Mumbai Indians");
        data.add(mode2);
        mode3.setSquadName("Delhi Capitals");
        data.add(mode3);
        return data;
    }
}