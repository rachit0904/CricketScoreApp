package com.cricketexchange.project.ui.schedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketexchange.project.R;

    public class scheduleFrag extends Fragment implements View.OnClickListener {
    ImageView notifyBtn,filterBtn;
    boolean flag=false;
    View view;
    int page;
    String notify;
    TabLayout tabLayout;
    ViewPager pager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.schedule_fragment, container, false);
    }
}