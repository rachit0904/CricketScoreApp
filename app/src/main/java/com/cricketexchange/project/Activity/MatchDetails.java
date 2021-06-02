package com.cricketexchange.project.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Pager.MatchDetailPager;
import com.cricketexchange.project.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MatchDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView team1, team2, back;
    TextView t1Name, t2Name, t1Score, t2Score, t1Overs, t2Overs, matchTitle, cms, startdate;
    String st1Name, st2Name, st1Score, st2Score, st1Overs, st2Overs, sstartdate, status, st1url, st2url, matchsumm;
    public TabLayout tabLayout;
    public ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        TextView textView = findViewById(R.id.matchTitle);
        textView.setText(getIntent().getStringExtra("match"));
        initializeIds();
        setData();
        back.setOnClickListener(this);
        MatchDetailPager matchDetailPager = new MatchDetailPager(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(matchDetailPager);
        tabLayout.setupWithViewPager(pager);
        tabLayout.selectTab(tabLayout.getTabAt(2));
        pager.setCurrentItem(2);
        load();
    }

    private void setData() {

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
        if (st1url.trim().length() != 0) {
            Picasso.get().load(st1url).into(team1);
        }
        if (st2url.trim().length() != 0) {
            Picasso.get().load(st2url).into(team2);
        }
        t1Name.setText(st1Name);
        t2Name.setText(st2Name);
        t1Score.setText(st1Score);
        t2Score.setText(st2Score);
        t1Overs.setText(st1Overs);
        t2Overs.setText(st2Overs);
        cms.setText(matchsumm);
        startdate.setText(sstartdate);
        LinearLayout startdate = findViewById(R.id.startTimerLayout);
        LinearLayout t1ScoreLayout = findViewById(R.id.t1ScoreLayout);
        LinearLayout t2ScoreLayout = findViewById(R.id.t2ScoreLayout);
        if (status.equalsIgnoreCase("UPCOMING")) {
            t1ScoreLayout.setVisibility(View.GONE);
            t2ScoreLayout.setVisibility(View.GONE);
            startdate.setVisibility(View.VISIBLE);
        } else {
            startdate.setVisibility(View.GONE);
            t1ScoreLayout.setVisibility(View.VISIBLE);
            t2ScoreLayout.setVisibility(View.VISIBLE);
        }
    }


    private void load() {
        new Load().execute(Constants.HOST + "getMatchesHighlight?sid=2796&mid=51038");
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
                    JSONObject data = object.getJSONObject("data");
                    //  JSONObject meta = data.getJSONObject("meta");
                    //  String CRR = meta.getString("currentRunRate");
                    //  String RRR = meta.getString("requiredRunRate");
                    JSONObject matchDetail = data.getJSONObject("matchDetail");
                    JSONObject matchSummary = matchDetail.getJSONObject("matchSummary");
                    JSONObject hometeam = matchSummary.getJSONObject("homeTeam");
                    JSONObject awayTeam = matchSummary.getJSONObject("awayTeam");
                    JSONObject scores = matchSummary.getJSONObject("scores");
                    status = matchSummary.getString("status");

                    st1Name = hometeam.getString("shortName");
                    st2Name = awayTeam.getString("shortName");
                    st1Score = scores.getString("homeScore");
                    st1Overs = scores.getString("homeOvers");
                    st2Score = scores.getString("awayScore");
                    st2Overs = scores.getString("awayOvers");
                    matchsumm = matchSummary.getString("matchSummaryText");
                    sstartdate = matchSummary.getString("localStartTime");
                    st1url = hometeam.getString("logoUrl");
                    st2url = awayTeam.getString("logoUrl");
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();
        }
    }


}

//todo upcomig->t1(gone)->t2(gone) start timer lyout(visibl) match start tme ->local star time