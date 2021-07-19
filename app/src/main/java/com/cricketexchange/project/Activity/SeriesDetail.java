package com.cricketexchange.project.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Pager.SeriesViewPager;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SeriesDetail extends AppCompatActivity implements View.OnClickListener {
    TextView seriesTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);
        ImageButton bck = findViewById(R.id.bckbutton);
        bck.setOnClickListener(this);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.pager);
        TextView textView = findViewById(R.id.inboxTitle);
        textView.setText(getIntent().getStringExtra("name"));
        SeriesViewPager adapter = new SeriesViewPager(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        MobileAds.initialize(this, initializationStatus -> {
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
        String s1 = sharedPreferences.getString("ban1", "ca-app-pub-3940256099942544/6300978111");

        RelativeLayout mAdView = findViewById(R.id.adView);
        mAdView.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        AdView mAdview = new AdView(this);
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) mAdView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mAdview.setLayoutParams(layoutParams);
        mAdview.setAdSize(AdSize.BANNER);
        mAdview.setAdUnitId(s1);
        mAdView.addView(mAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        loadinterstitialads();
        loadinterstitialads();
    }
    InterstitialAd mInterstitialAd;

    private void loadinterstitialads() {
        Random rand = new Random();
        prepareAd();
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> runOnUiThread(() -> {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                prepareAd();
                Log.d("TAG", " Interstitial not loaded");
            }

        }), rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, TimeUnit.SECONDS);
    }

    public void prepareAd() {

        SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
        String i1 = sharedPreferences.getString("in2", "ca-app-pub-3940256099942544%2F1033173712");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(i1);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bckbutton) {
            finish();
        }
    }
}