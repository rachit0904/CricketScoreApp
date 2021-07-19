package com.cricketexchange.project.Activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Pager.MatchDetailPager;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MatchDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView team1, team2, back;
    TextView t1Name, t2Name, t1Score, t2Score, t1Overs, t2Overs, matchTitle, cms, startdate;
    String st1Name, st2Name, st1Score, st2Score, st1Overs, st2Overs, sstartdate, status, st1url, st2url, matchsumm;
    public TabLayout tabLayout;
    public ViewPager pager;
    String sid;
    String mid;
    String HOST;
    private RelativeLayout mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        TextView textView = findViewById(R.id.matchTitle);
        textView.setText(getIntent().getStringExtra("match"));
        initializeIds();
        back.setOnClickListener(this);
        HOST = getIntent().getStringExtra("HOST");
        MatchDetailPager matchDetailPager = new MatchDetailPager(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(matchDetailPager);
        tabLayout.setupWithViewPager(pager);
        tabLayout.selectTab(tabLayout.getTabAt(2));
        pager.setCurrentItem(2);
        sid = getIntent().getStringExtra("sid");
        mid = getIntent().getStringExtra("mid");
        LinearLayout t1ScoreLayout = findViewById(R.id.t1ScoreLayout);
        LinearLayout t2ScoreLayout = findViewById(R.id.t2ScoreLayout);
        RelativeLayout notifyLayout = findViewById(R.id.upcomingNotifyLayout);
        LinearLayout startD = findViewById(R.id.startTimerLayout);
        load();
        if (getIntent().getStringExtra("status").equalsIgnoreCase("UPCOMING")) {
            t1ScoreLayout.setVisibility(View.GONE);
            t2ScoreLayout.setVisibility(View.GONE);
            startD.setVisibility(View.VISIBLE);
            notifyLayout.setVisibility(View.VISIBLE);
            pager.setVisibility(View.GONE);
        } else {
            startD.setVisibility(View.GONE);
            t1ScoreLayout.setVisibility(View.VISIBLE);
            t2ScoreLayout.setVisibility(View.VISIBLE);
            notifyLayout.setVisibility(View.GONE);
            pager.setVisibility(View.VISIBLE);
        }


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
        String s1 = sharedPreferences.getString("ban1", "ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adView);
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


    private void initializeIds() {
        back = findViewById(R.id.bckBtn);
        team1 = findViewById(R.id.t1icon);
        team2 = findViewById(R.id.t2icon);
        t1Name = findViewById(R.id.t1Name);
        t2Name = findViewById(R.id.t2Name);
        t1Score = findViewById(R.id.t1Score);
        t1Overs = findViewById(R.id.t1Overs);
        t2Score = findViewById(R.id.t2Score);
        t2Overs = findViewById(R.id.t2Overs);
        matchTitle = findViewById(R.id.matchTitle);
        startdate = findViewById(R.id.matchStartTime);
        cms = findViewById(R.id.cms);
        tabLayout = findViewById(R.id.tabLayout4);
        pager = findViewById(R.id.matchDetailPager);
    }

    @Override
    public void onClick(View v) {
        if (v == back) {
            finish();
        }
    }

    private void update() {
        if (st1url != null) {
            if (st1url.trim().length() != 0) {
                Picasso.get().load(st1url).into(team1);
            }
        }
        if (st2url != null) {
            if (st2url.trim().length() != 0) {
                Picasso.get().load(st2url).into(team2);
            }
        }


        t1Name.setText(st1Name);
        t2Name.setText(st2Name);
        if (!status.equalsIgnoreCase("UPCOMING")) {
            t1Score.setText((st1Score));
            t2Score.setText((st2Score));
            t1Overs.setText((st1Overs));
            t2Overs.setText((st2Overs));
        }
        cms.setText(matchsumm);
        startdate.setText(sstartdate);
    }

    private void load() {
                if (!sid.isEmpty() || !mid.isEmpty()) {
                    loadScores(sid,mid);
//                    new Load().execute(HOST + "getMatchesHighlight?sid=" + Integer.parseInt(sid) + "&mid=" + Integer.parseInt(mid));
                }
    }

    private void loadScores(String sid, String mid) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("MatchesHighlight");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                DataSnapshot ds = snapshot.child(sid + "S" + mid).child("jsondata").child("matchDetail").child("matchSummary");
                try {
                    st1url = ds.child("homeTeam").child("logoUrl").getValue().toString();
                    st2url = ds.child("awayTeam").child("logoUrl").getValue().toString();
                status = ds.child("status").getValue().toString();
                st1Name = ds.child("homeTeam").child("shortName").getValue().toString();
                st2Name = ds.child("awayTeam").child("shortName").getValue().toString();
                matchsumm = ds.child("matchSummaryText").getValue().toString();
                sstartdate = ds.child("localStartDate").getValue().toString();
                    if (!status.equalsIgnoreCase("UPCOMING")) {
                        st1Score = ds.child("scores").child("homeScore").getValue().toString();
                        st1Overs = ds.child("scores").child("homeOvers").getValue().toString();
                        st2Score = ds.child("scores").child("awayScore").getValue().toString();
                        st2Overs = ds.child("scores").child("awayOvers").getValue().toString();
                    }else{
                        st1Score="";
                        st2Score="";
                        st1Overs="";
                        st2Overs="";
                    }
                }catch (Exception e){
                    status = getIntent().getExtras().getString("status");
                    st1Name =getIntent().getExtras().getString("t1nme");
                    st2Name = getIntent().getExtras().getString("t2nme");
                    matchsumm = getIntent().getExtras().getString("matchSumm");
                    sstartdate = getIntent().getExtras().getString("startTime");
                    st1url=getIntent().getExtras().getString("t1logo");
                    st2url=getIntent().getExtras().getString("t2logo");
                }
                    update();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
