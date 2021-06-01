package com.cricketexchange.project.ui.matchdetail.info;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.cricketexchange.project.Activity.SeriesDetail;
import com.cricketexchange.project.Activity.TeamPlayersInfo;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MatchInfo extends Fragment implements View.OnClickListener {
    TextView tossResult, seriesName, matchType, startDateTime, venue, pt1SName, pt1FName, pt2SName, pt2FName, primaryUmpire, thirdUmpire, refree;
    CardView seriesCard;
    ImageView t1Logo, t2Logo;
    RelativeLayout t1Layout, t2Layout;
    ProgressBar progressBar;
    View view;
    String ATseriesName, ATmatchType, ATvenueName, ATumpires, ATtumpire, ATrefree, ATtossMessage, ATstartdate,
            t1name, t1Sname, t1logourl, t1bg, t1color, t2name, t2Sname, t2logourl, t2bg, t2color, t1id, t2id;
    String sid = "2802", mid = "51058";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_match_info, container, false);
        initialize();
        //setData();
        progressBar = view.findViewById(R.id.progressBar);
        load();

        seriesCard.setOnClickListener(this);
        t1Layout.setOnClickListener(this);
        t2Layout.setOnClickListener(this);
        return view;
    }

    private void setData() {
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute("http://3.108.39.214/getMatchesHighlight?sid=" + sid + "&mid=" + mid + "");
    }

    private void initialize() {
        tossResult = view.findViewById(R.id.tossStatus);
        seriesName = view.findViewById(R.id.tounamentName);
        seriesCard = view.findViewById(R.id.showSeriesDetail);
        matchType = view.findViewById(R.id.seriesType);
        venue = view.findViewById(R.id.venue);
        startDateTime = view.findViewById(R.id.startTime);
        pt1SName = view.findViewById(R.id.t1shortName);
        pt1FName = view.findViewById(R.id.t1fullName);
        t1Layout = view.findViewById(R.id.teamcard);
        t1Logo = view.findViewById(R.id.pt1Logo);
        pt2SName = view.findViewById(R.id.t2shortName);
        pt2FName = view.findViewById(R.id.t2fullName);
        t2Layout = view.findViewById(R.id.teamcard2);
        t2Logo = view.findViewById(R.id.pt2Logo);
        primaryUmpire = view.findViewById(R.id.umpires);
        thirdUmpire = view.findViewById(R.id.thirdUmpire);
        refree = view.findViewById(R.id.refree);
    }

    private void update() {
        progressBar.setVisibility(View.GONE);
        if (t1logourl.trim().length() != 0) {
            Picasso.get().load(t1logourl).into(t1Logo);
        }


        if (t2logourl.trim().length() != 0) {
            Picasso.get().load(t2logourl).into(t2Logo);
        }


        if (ATmatchType.trim().length() != 0) {
            matchType.setText(ATmatchType);
        }
        if (ATseriesName.trim().length() != 0) {
            seriesName.setText(ATseriesName);
        }
        if (ATtossMessage.trim().length() != 0) {
            tossResult.setText(ATtossMessage);
        }
        if (ATstartdate.trim().length() != 0) {
            startDateTime.setText(ATstartdate);
        }
        if (t1Sname.trim().length() != 0) {
            pt1SName.setText(t1Sname);
        }
        if (t1name.trim().length() != 0) {
            pt1FName.setText(t1name);
        }

        if (t2Sname.trim().length() != 0) {
            pt2SName.setText(t2Sname);
        }
        if (t2name.trim().length() != 0) {
            pt2FName.setText(t2name);
        }


        if (ATtumpire.trim().length() != 0) {
            thirdUmpire.setText(ATtumpire);
        }
        if (ATumpires.trim().length() != 0) {
            primaryUmpire.setText(ATumpires);
        }
        if (ATvenueName.trim().length() != 0) {
            venue.setText(ATvenueName);
        }

        if (ATrefree.trim().length() != 0) {
            refree.setText(ATrefree);
        }


    }

    @Override
    public void onClick(View v) {
        if (v == seriesCard) {
            //pass intent to series Detail page
            Intent intent = new Intent(getContext(), SeriesDetail.class);
            intent.putExtra("sid", sid);
            intent.putExtra("sname", ATseriesName);
            startActivity(intent);
        }
        if (v == t1Layout) {
            //pass intent to team info page
            Intent intent = new Intent(getContext(), TeamPlayersInfo.class);
            intent.putExtra("tid", t1id);
            startActivity(intent);
        }
        if (v == t2Layout) {
            //pass intent to team info page
            Intent intent = new Intent(getContext(), TeamPlayersInfo.class);
            intent.putExtra("tid", t2id);
            startActivity(intent);
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
                    JSONObject data = object.getJSONObject("data");

                    ATseriesName = data.getJSONObject("meta").getJSONObject("series").getString("name");
                    ATmatchType = data.getJSONObject("meta").getString("matchType");
                    ATvenueName = data.getJSONObject("meta").getString("venueName");
                    JSONObject umpires = data.getJSONObject("matchDetail").getJSONObject("umpires");
                    ATumpires = umpires.getJSONObject("firstUmpire").getString("name") + " , " + umpires.getJSONObject("secondUmpire").getString("name");
                    ATtumpire = umpires.getJSONObject("thirdUmpire").getString("name");
                    ATrefree = umpires.getJSONObject("referee").getString("name");
                    ATtossMessage = data.getJSONObject("matchDetail").getString("tossMessage");
                    JSONObject hometeam = data.getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("homeTeam");
                    JSONObject awayTeam = data.getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("awayTeam");
                    t1name = hometeam.getString("name");
                    t1id = hometeam.getString("id");
                    t1Sname = hometeam.getString("shortName");
                    t1logourl = hometeam.getString("logoUrl");
                    t1bg = hometeam.getString("backgroundImageUrl");
                    t1color = hometeam.getString("teamColour");
                    t2name = awayTeam.getString("name");
                    t2id = awayTeam.getString("id");
                    t2logourl = awayTeam.getString("logoUrl");
                    t2Sname = awayTeam.getString("shortName");
                    t2bg = awayTeam.getString("backgroundImageUrl");
                    t2color = awayTeam.getString("teamColour");
                    String ATstartdat = data.getJSONObject("matchDetail").getJSONObject("matchSummary").getString("startDateTime");
                    ATstartdate = ATstartdat.split("T")[0] + " " + ATstartdat.split("T")[1].split("Z")[0];


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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