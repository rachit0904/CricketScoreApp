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

public class schdeule extends Fragment implements View.OnClickListener {
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
        notifyBtn.setOnClickListener(this);
        tabLayout = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager2);
        ScheduleViewPager adapter = new ScheduleViewPager(getChildFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        setDefault();
        return view;
    }

    @Override
    public void onClick(View v) {
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
            } else {
                notifyBtn.setBackground(notifyBtn.getContext().getResources().getDrawable(R.drawable.notify));
                Snackbar.make(v, "notification turned off for all upcoming matches!", Snackbar.LENGTH_SHORT).show();
                notifyFlag = false;
                setPreference();
            }
        }

    }

    public void upcomingMatchesNotification(Context context, String title, String text) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("demo", "Daily Match Update", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(text);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "demo");
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.ic_baseline_sports_cricket_24);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setStyle(bigText);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, builder.build());

    }

    private void refresh() {
        boolean connection = isNetworkAvailable();
        if (connection == false) {
            startActivity(new Intent(getContext(), NetworkFailureActivity.class));
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
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