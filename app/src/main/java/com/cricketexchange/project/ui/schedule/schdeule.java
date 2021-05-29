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

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.cricketexchange.project.Activity.NetworkFailureActivity;
import com.cricketexchange.project.Pager.ScheduleViewPager;
import com.cricketexchange.project.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class schdeule extends Fragment implements View.OnClickListener {
    ImageView notifyBtn,filterBtn;
    boolean notifyFlag = false,filterFlag=false;
    View view;
    String notify;
    TabLayout tabLayout;
    ViewPager pager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    HorizontalScrollView scrollView;
    ChipGroup tours;
    Chip all,test,t20,odi,international,league,women;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_schdeule, container, false);
        preferences = getActivity().getSharedPreferences("prefs", 0);
        notifyBtn = view.findViewById(R.id.notifyBtn);
        filterBtn = view.findViewById(R.id.filterBtn);
        scrollView=view.findViewById(R.id.scroll);
        notifyBtn.setOnClickListener(this);
        filterBtn.setOnClickListener(this);
        tabLayout = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager2);
        ScheduleViewPager adapter = new ScheduleViewPager(getChildFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tours=view.findViewById(R.id.tours);
        all=view.findViewById(R.id.all);t20=view.findViewById(R.id.t20);odi=view.findViewById(R.id.odi);
        test=view.findViewById(R.id.test);league=view.findViewById(R.id.league);women=view.findViewById(R.id.women);
        international=view.findViewById(R.id.international);
        all.setChecked(true);all.setChipBackgroundColorResource(android.R.color.holo_green_dark);all.setCloseIconEnabled(true);
        all.setOnClickListener(this);test.setOnClickListener(this);odi.setOnClickListener(this);t20.setOnClickListener(this);
        league.setOnClickListener(this);international.setOnClickListener(this);women.setOnClickListener(this);
        setDefault();
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == filterBtn){
            if(filterFlag){
                scrollView.setVisibility(View.VISIBLE);
                filterFlag=false;
            }else{
                scrollView.setVisibility(View.GONE);
                filterFlag=true;
            }
        }
        if (v == notifyBtn) {
            if (!notifyFlag) {
                notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notifyon));
                Snackbar.make(view, "notification turned on for all upcoming matches!", Snackbar.LENGTH_SHORT).show();
                notifyFlag = true;
                upcomingMatchesNotification(getContext(),
                        "Congratulations !",
                        "you are getting this notification as you subscribed to get notification"
                        );
                setPreference();
            }
            else {
                notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notify));
                Snackbar.make(v, "notification turned off for all upcoming matches!", Snackbar.LENGTH_SHORT).show();
                notifyFlag = false;
                setPreference();
            }
        }
        if(v == all) {
            if (all.isChecked()) {
                all.setCloseIconEnabled(true);test.setCloseIconEnabled(false);t20.setCloseIconEnabled(false);odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);international.setCloseIconEnabled(false);women.setCloseIconEnabled(false);
                all.setChipBackgroundColorResource(android.R.color.holo_green_dark);t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChecked(true);test.setChecked(false);t20.setChecked(false);odi.setChecked(false);
                league.setChecked(false);international.setChecked(false);women.setChecked(false);
            }
        }
        if(v == odi){
                if(odi.isChecked()) {
                    odi.setCloseIconEnabled(true);test.setCloseIconEnabled(false);t20.setCloseIconEnabled(false);all.setCloseIconEnabled(false);
                    league.setCloseIconEnabled(false);international.setCloseIconEnabled(false);women.setCloseIconEnabled(false);
                    odi.setChipBackgroundColorResource(android.R.color.holo_green_dark);t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                    all.setChipBackgroundColorResource(R.color.scoreRowBackground);test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                    league.setChipBackgroundColorResource(R.color.scoreRowBackground);international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                    women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                    odi.setChecked(true);test.setChecked(false);t20.setChecked(false);all.setChecked(false);
                    league.setChecked(false);international.setChecked(false);women.setChecked(false);
            }
        }
        if(v == t20){
            if(t20.isChecked()) {
                t20.setCloseIconEnabled(true);test.setCloseIconEnabled(false);all.setCloseIconEnabled(false);odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);international.setCloseIconEnabled(false);women.setCloseIconEnabled(false);
                t20.setChipBackgroundColorResource(android.R.color.holo_green_dark);all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                t20.setChecked(true);test.setChecked(false);all.setChecked(false);odi.setChecked(false);
                league.setChecked(false);international.setChecked(false);women.setChecked(false);
            } }
        if(v == test){
            if(test.isChecked()) {
                test.setCloseIconEnabled(true);all.setCloseIconEnabled(false);t20.setCloseIconEnabled(false);odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);international.setCloseIconEnabled(false);women.setCloseIconEnabled(false);
                test.setChipBackgroundColorResource(android.R.color.holo_green_dark);t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                test.setChecked(true);all.setChecked(false);t20.setChecked(false);odi.setChecked(false);
                league.setChecked(false);international.setChecked(false);women.setChecked(false);
            }
        }
        if(v == international){
            if(international.isChecked()) {
                international.setCloseIconEnabled(true);test.setCloseIconEnabled(false);t20.setCloseIconEnabled(false);odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);all.setCloseIconEnabled(false);women.setCloseIconEnabled(false);
                international.setChipBackgroundColorResource(android.R.color.holo_green_dark);t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                international.setChecked(true);test.setChecked(false);t20.setChecked(false);odi.setChecked(false);
                league.setChecked(false);all.setChecked(false);women.setChecked(false);
            }
        }
        if(v == league){
            if(league.isChecked()) {
                league.setCloseIconEnabled(true);test.setCloseIconEnabled(false);t20.setCloseIconEnabled(false);odi.setCloseIconEnabled(false);
                all.setCloseIconEnabled(false);international.setCloseIconEnabled(false);women.setCloseIconEnabled(false);
                league.setChipBackgroundColorResource(android.R.color.holo_green_dark);t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChipBackgroundColorResource(R.color.scoreRowBackground);international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChecked(true);test.setChecked(false);t20.setChecked(false);odi.setChecked(false);
                all.setChecked(false);international.setChecked(false);women.setChecked(false);
            }
        }
        if(v == women){
            if(women.isChecked()) {
                women.setCloseIconEnabled(true);test.setCloseIconEnabled(false);t20.setCloseIconEnabled(false);odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);international.setCloseIconEnabled(false);all.setCloseIconEnabled(false);
                women.setChipBackgroundColorResource(android.R.color.holo_green_dark);t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChecked(true);test.setChecked(false);t20.setChecked(false);odi.setChecked(false);
                league.setChecked(false);international.setChecked(false);all.setChecked(false);
            }
        }
    }

    public void upcomingMatchesNotification(Context context, String title, String text) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("demo","Daily Match Update", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(text);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"demo");
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.ic_baseline_sports_cricket_24);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setStyle(bigText);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(context);
        managerCompat.notify(1,builder.build());

    }

    private void refresh(){
        boolean connection=isNetworkAvailable();
        if(connection==false){
            startActivity(new Intent(getContext(), NetworkFailureActivity.class));
        }
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
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

    private void setPreference() {
        editor = preferences.edit();
        editor.putBoolean("notify user", notifyFlag);
        editor.apply();
    }

}