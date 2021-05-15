package com.cricketexchange.project.ui.schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cricketexchange.project.Pager.ScheduleViewPager;
import com.cricketexchange.project.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

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
        view= inflater.inflate(R.layout.schedule_fragment, container, false);
        preferences=getActivity().getSharedPreferences("prefs",0);
        notifyBtn=view.findViewById(R.id.notifyBtn);
        tabLayout=view.findViewById(R.id.tabLayout2);
        pager=view.findViewById(R.id.pager2);
        ScheduleViewPager adapter=new ScheduleViewPager(getActivity().getSupportFragmentManager(),tabLayout.getTabCount(),getContext());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        setDefault();
        notifyBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v==notifyBtn){
            if(flag==false) {
                notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notifyon));
                Snackbar.make(view,"notification turned on for all upcoming matches!",Snackbar.LENGTH_SHORT).show();
                flag=true;
                setPreference();
            }else{
                notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notify));
                Snackbar.make(v,"notification turned off for all upcoming matches!",Snackbar.LENGTH_SHORT).show();
                flag=false;
                setPreference();
            }
        }
    }

    private void setDefault() {
        notify= String.valueOf(getActivity().getSharedPreferences("prefs",0).getBoolean("notify user",false));
        if(notify=="false"){
            flag=false;
            notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notify));
        }else{
            flag=true;
            notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notifyon));
        }
    }

    private void setPreference() {
        editor=preferences.edit();
        editor.putBoolean("notify user",flag);
        editor.commit();
    }

}