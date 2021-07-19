package com.cricketexchange.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cricketexchange.project.Adapter.Recyclerview.PlayerDataAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.PlayersDataModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TeamPlayersInfo extends AppCompatActivity implements View.OnClickListener {
    ImageView bck, teamLogo;
    TextView teamShortName, teamFullName;
    RecyclerView recyclerView;
    Toolbar materialToolbar;
    String tid = null;
    String teamsrt, teamlong, teamcolor;
    String teamname;
    final List<PlayersDataModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_players_info);
        materialToolbar = findViewById(R.id.toolbar4);
        tid = getIntent().getStringExtra("tid");
        teamsrt = getIntent().getStringExtra("tsn");
        teamlong = getIntent().getStringExtra("tln");
        teamcolor = getIntent().getStringExtra("tcl");
        teamname = getIntent().getStringExtra("sname");
        TextView title = findViewById(R.id.title);
        title.setText(teamname);
        bck = findViewById(R.id.back);
        teamLogo = findViewById(R.id.pt1Logo);
        teamShortName = findViewById(R.id.teamShortName);
        teamFullName = findViewById(R.id.teamFullName);
        recyclerView = findViewById(R.id.players);
        teamShortName.setText(teamsrt);
        if (teamlong.trim().length() != 0) {
            Picasso.get().load(teamlong).into(teamLogo);
        }
        teamFullName.setText(teamname);
        if (teamcolor.isEmpty()) {
            teamShortName.setTextColor(Color.WHITE);
        } else {
            teamShortName.setTextColor(Color.parseColor(teamcolor));
        }
        load();
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


    private void load() {
        loadTeamPlayers(tid);
    }

    private void loadTeamPlayers(String tid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Players");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                try {
                    if (snapshot.hasChild(tid)) {
                        for (int i = 0; i < snapshot.child(tid).child("players").getChildrenCount(); i++) {
                            PlayersDataModel playersDataModel = new PlayersDataModel();
                            playersDataModel.setId(snapshot.child(tid).child("players").child(String.valueOf(i)).child("playerId").getValue().toString());
                            playersDataModel.setName(snapshot.child(tid).child("players").child(String.valueOf(i)).child("fullName").getValue().toString());
                            playersDataModel.setBattingStyle(snapshot.child(tid).child("players").child(String.valueOf(i)).child("battingStyle").getValue().toString());
                            playersDataModel.setBowlingStyle(snapshot.child(tid).child("players").child(String.valueOf(i)).child("bowlingStyle").getValue().toString());
                            playersDataModel.setPlayerType(snapshot.child(tid).child("players").child(String.valueOf(i)).child("playerType").getValue().toString());
                            playersDataModel.setLogoUrl("NA");
                            if (playersDataModel.getPlayerType().trim().length() == 0) {
                                playersDataModel.setPlayerType("NA");
                            }
                            if (playersDataModel.getBowlingStyle().trim().length() == 0) {
                                playersDataModel.setBowlingStyle("NA");
                            }
                            if (playersDataModel.getBattingStyle().trim().length() == 0) {
                                playersDataModel.setBattingStyle("NA");
                            }
                            list.add(playersDataModel);
                        }
                        update();
                    } else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private void update() {
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlayerDataAdapter adapter = new PlayerDataAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == bck) {
            finish();
        }
    }

}
