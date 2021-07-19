package com.cricketexchange.project.ui.schedule;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cricketexchange.project.Activity.NetworkFailureActivity;
import com.cricketexchange.project.Pager.ScheduleViewPager;
import com.cricketexchange.project.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class schdeule extends Fragment  {
    ImageView notifyBtn;
    boolean notifyFlag = false, filterFlag = false;
    View view;
    String notify;
    TabLayout tabLayout;
    ViewPager pager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    HorizontalScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schdeule, container, false);
        preferences = getActivity().getSharedPreferences("prefs", 0);
        notifyBtn = view.findViewById(R.id.notifyBtn);
        scrollView = view.findViewById(R.id.scroll);
        tabLayout = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager2);
        ScheduleViewPager adapter = new ScheduleViewPager(getChildFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        setDefault();
        return view;
    }

    private void setDefault() {
        notify = String.valueOf(getActivity().getSharedPreferences("prefs", 0).getBoolean("notify user", false));
        if (notify == "false") {
            notifyFlag = false;
            notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notify));
        } else {
            notifyFlag = true;
            notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notifyon));
        }
    }

}