package com.cricketexchange.project.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.News.newsFrag;
import com.cricketexchange.project.ui.home.homeFrag;
import com.cricketexchange.project.ui.more.moreFrag;
import com.cricketexchange.project.ui.schedule.ScheduleFrag;
import com.cricketexchange.project.ui.series.SeriesFrag;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 5;

    // The AdLoader used to load ads.
    private AdLoader adLoader;

    // List of MenuItems and native ads that populate the RecyclerView.
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.selectTab(tabLayout.getTabAt(2));
        addFragment(new homeFrag());
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //ads settings


    }


    private void setFragment(int position) {
        switch (position + 1) {
            case 1: {
                addFragment(new newsFrag());
                break;
            }
            case 2: {
                addFragment(new SeriesFrag());
                break;
            }
            case 3: {
                addFragment(new homeFrag());
                break;
            }
            case 4: {
                addFragment(new ScheduleFrag());
                break;
            }
            case 5: {
                addFragment(new moreFrag());
                break;
            }
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    //ads setting






}