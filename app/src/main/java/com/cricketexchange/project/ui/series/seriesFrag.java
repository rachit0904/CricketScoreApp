package com.cricketexchange.project.ui.series;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.SeriesNameAdapter;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.schedule.scheduleFrag;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class seriesFrag extends Fragment implements View.OnClickListener {
    RecyclerView seriesRv;
    FragmentManager manager;
    Button seeAllBtn;
    RecyclerView.Adapter adapter;
    ArrayList<SeriesModel> data=new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.series_fragment, container, false);
        seriesRv=root.findViewById(R.id.seriesRv);
        seeAllBtn=root.findViewById(R.id.allSeries);
        seeAllBtn.setOnClickListener(this);
        seriesRv.hasFixedSize();
        seriesRv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        data=getData();
        adapter=new SeriesNameAdapter(getContext(),data);
        seriesRv.setAdapter(adapter);
        return root;
    }

    private ArrayList<SeriesModel> getData() {
        ArrayList<SeriesModel> list=new ArrayList<>();
        SeriesModel model=new SeriesModel();
        for(int i=0;i<4;i++){
            model.setSeriesName("Indian Premiere League 2021");
            list.add(model);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.allSeries){
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            tabLayout.selectTab(tabLayout.getTabAt(3));
            addFragment(new scheduleFrag());
        }
    }

    public void addFragment(Fragment fragment) {
        manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

}