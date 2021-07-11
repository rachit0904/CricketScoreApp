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
import android.widget.TextView;

import com.cricketexchange.project.Adapter.Recyclerview.PlayerDataAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.PlayersDataModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

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
    private String HOST = "";
    ImageView bck, teamLogo;
    TextView teamShortName, teamFullName;
    RecyclerView recyclerView;
    Toolbar materialToolbar;
    String tid = null;
    String teamsrt, teamlong, teamcolor;
    String seriesname, teamname;
    Boolean notifyFlag = false;
    List<PlayersDataModel> list = new ArrayList<>();

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
        HOST = getIntent().getStringExtra("HOST");
        TextView title = findViewById(R.id.title);
        title.setText(teamname);
        bck = findViewById(R.id.back);
        teamLogo = findViewById(R.id.pt1Logo);
        teamShortName = findViewById(R.id.teamShortName);
        teamFullName = findViewById(R.id.teamFullName);
        bck.setOnClickListener(this);
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

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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


    private void load() {
        new Load().execute(HOST + "getAllPlayerByTID?id=" + tid);

    }


    private void update() {

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlayerDataAdapter adapter = new PlayerDataAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notification) {
            if (!notifyFlag) {
                //TOOD add team to db for verification and further process of sending notification
                item.setIcon(getResources().getDrawable(R.drawable.notifyon));
                Snackbar.make(findViewById(R.id.notification), "notification turned on for this team's all upcoming matches!", Snackbar.LENGTH_SHORT).show();
                notifyFlag = true;
                setPreference();
            } else {
                item.setIcon(getResources().getDrawable(R.drawable.notify));
                Snackbar.make(findViewById(R.id.notification), "notification turned off!", Snackbar.LENGTH_SHORT).show();
                notifyFlag = false;
                setPreference();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDefaults() {
        // TODO set notification on or off on bases of whether teamId set for notification [from db]
    }

    public void teamMatchNotification(Context context, String title, String text) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("demo2", "team match Update", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(text);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "demo2");
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.ic_baseline_sports_cricket_24);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setStyle(bigText);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, builder.build());

    }

    private void setPreference() {
        SharedPreferences preferences = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("team notification", notifyFlag);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        if (v == bck) {
            finish();
        }
    }

    private class Load extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... urls) {
            long totalSize = 0;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray data = object.getJSONObject("data").getJSONObject("teamPlayers").getJSONArray("players");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);


                        try {
                            PlayersDataModel playersDataModel = new PlayersDataModel();
                            playersDataModel.setId(obj.getString("playerId"));
                            playersDataModel.setName(obj.getString("fullName"));
                            playersDataModel.setBattingStyle(obj.getString("battingStyle"));
                            playersDataModel.setBowlingStyle(obj.getString("bowlingStyle"));
                            playersDataModel.setPlayerType("NA");
                            playersDataModel.setLogoUrl("NA");
                            if (playersDataModel.getBowlingStyle().trim().length() == 0) {
                                playersDataModel.setBowlingStyle("NA");
                            }
                            if (playersDataModel.getBattingStyle().trim().length() == 0) {
                                playersDataModel.setBattingStyle("NA");
                            }
                            list.add(playersDataModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.e("ASYNCTASK Child", String.valueOf(childList.size()));
            //   Log.e("ASYNCTASK Dates", String.valueOf(dates.size()));

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();
        }
    }

}
