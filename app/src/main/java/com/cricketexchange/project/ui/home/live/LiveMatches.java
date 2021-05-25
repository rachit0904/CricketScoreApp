package com.cricketexchange.project.ui.home.live;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.MatchesChildAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.schedule.schdeule;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LiveMatches extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    CardView card;

    public LiveMatches() {
        super(R.layout.fragment_live_matches);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        card=view.findViewById(R.id.upcoming);
        card.setOnClickListener(this);
        recyclerView=view.findViewById(R.id.liveMatches);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MatchesChildAdapter adapter=new MatchesChildAdapter(getContext(),getChildData());
        recyclerView.setAdapter(adapter);
    }


    @NotNull
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

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v==card){
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            tabLayout.selectTab(tabLayout.getTabAt(3));
            addFragment(new schdeule());
        }
    }
}