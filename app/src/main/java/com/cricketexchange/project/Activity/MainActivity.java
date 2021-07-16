package com.cricketexchange.project.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.News.newsFrag;
import com.cricketexchange.project.ui.home.homeFrag;
import com.cricketexchange.project.ui.more.moreFrag;
import com.cricketexchange.project.ui.schedule.schdeule;
import com.cricketexchange.project.ui.series.seriesFrag;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.cricketexchange.project.Constants.Constants.HOSTNAME;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private AdView ss;
    String HOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        HOST = getIntent().getStringExtra("HOST");
        Log.e("onCreate: ", HOST);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
        schdeule schdeule = new schdeule();
        //demo notify test

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        loadinterstitialads();
        //ads settings
    }

    InterstitialAd mInterstitialAd;

    private void loadinterstitialads() {
        Random rand = new Random();
        prepareAd();
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> runOnUiThread(() -> {
            Boolean isShown=false;
            if (mInterstitialAd.isLoaded() && !isShown ) {
                mInterstitialAd.show();
                isShown=true;
            } else {
                Log.d("TAG", " Interstitial not loaded");
            }
            prepareAd();
        }), rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, TimeUnit.SECONDS);
    }

    public void prepareAd() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admov_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    private void setFragment(int position) {
        switch (position + 1) {
            case 1: {
                addFragment(new newsFrag());
                break;
            }
            case 2: {
                addFragment(new seriesFrag());
                break;
            }
            case 3: {
                addFragment(new homeFrag());
                break;
            }
            case 4: {
                addFragment(new schdeule());
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


}
