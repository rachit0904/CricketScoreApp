package com.cricketexchange.project.ui.schedule;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cricketexchange.project.Pager.ScheduleViewPager;
import com.cricketexchange.project.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class schdeule extends Fragment implements View.OnClickListener {
    ImageView notifyBtn, filterBtn,syncBtn;
    boolean flag = false;
    View view;
    String notify;
    TabLayout tabLayout;
    ViewPager pager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_schdeule, container, false);
        preferences = getActivity().getSharedPreferences("prefs", 0);
        notifyBtn = view.findViewById(R.id.notifyBtn);
        filterBtn = view.findViewById(R.id.filterBtn);
        syncBtn = view.findViewById(R.id.syncBtn);
        tabLayout = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager2);
        ScheduleViewPager adapter = new ScheduleViewPager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        setDefault();
        notifyBtn.setOnClickListener(this);
        filterBtn.setOnClickListener(this);
        syncBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == notifyBtn) {
            if (!flag) {
                notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notifyon));
                Snackbar.make(view, "notification turned on for all upcoming matches!", Snackbar.LENGTH_SHORT).show();
                flag = true;
                demoNotification();
                setPreference();
            } else {
                notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notify));
                Snackbar.make(v, "notification turned off for all upcoming matches!", Snackbar.LENGTH_SHORT).show();
                flag = false;
                setPreference();
            }
        }
        if(v == syncBtn){
            refresh();
        }
        if (v == filterBtn){
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            View view=(getLayoutInflater().inflate(R.layout.filter_dialog,null));
            builder.setView(view);
            builder.setCancelable(false);
            ImageView close=view.findViewById(R.id.closeBtn);
            RadioGroup group=view.findViewById(R.id.radioGroup);
            group.check(R.id.all);
            Button reset=view.findViewById(R.id.reset);Button apply=view.findViewById(R.id.apply);
            AlertDialog dialog=builder.create();
            dialog.show();
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    group.check(R.id.all);
                }
            });
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //TODO - apply filter to R.V.
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void demoNotification() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("demo","Daily Match Update", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("you are getting this notification as you subscribed to get notification");

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getContext(),"demo");
        builder.setContentTitle("Congratulations !");
        builder.setSmallIcon(R.drawable.trophyicon);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setStyle(bigText);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(getContext());
        managerCompat.notify(1,builder.build());

    }

    private void refresh(){
        getParentFragmentManager().beginTransaction().detach(this).add(R.id.frameLayout,new schdeule()).commit();
    }


    private void setDefault() {
        notify = String.valueOf(getActivity().getSharedPreferences("prefs", 0).getBoolean("notify user", false));
        if (notify == "false") {
            flag = false;
            notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notify));
        } else {
            flag = true;
            notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notifyon));
        }
    }

    private void setPreference() {
        editor = preferences.edit();
        editor.putBoolean("notify user", flag);
        editor.apply();
    }

}