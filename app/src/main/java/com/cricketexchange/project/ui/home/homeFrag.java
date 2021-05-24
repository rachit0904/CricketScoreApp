package com.cricketexchange.project.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cricketexchange.project.Pager.HomePager;
import com.cricketexchange.project.R;
import com.google.android.material.tabs.TabLayout;

public class homeFrag extends Fragment {
    TabLayout tabLayout;
    ViewPager pager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment, container, false);
        pager=view.findViewById(R.id.homePager);
        tabLayout=view.findViewById(R.id.tabLayout3);
        HomePager adapter = new HomePager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        return view;
    }



}