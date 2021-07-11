package com.cricketexchange.project.ui.matchdetail.live;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.SplashScreen;
import com.cricketexchange.project.Adapter.Recyclerview.CurrentInningBattingAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.PartnershipsAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.SessionRecyclerAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.CommentaryModal;
import com.cricketexchange.project.Models.OverBallScoreModel;
import com.cricketexchange.project.Models.PartnershipsModal;
import com.cricketexchange.project.Models.SessionsDataModel;
import com.cricketexchange.project.Models.battingCardModal;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.matchdetail.commentary.Commentary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.cricketexchange.project.Constants.Constants.HOSTSUFFIX;

public class Live extends Fragment implements View.OnClickListener {
    TextView currentBatsman1, currentBatsman2, currentBatsman1Score, currentBatsman2Score, currentBowler, currentBowlerScore, RRR, CRR;
    TextView currentInningTeamName, currentInningTeamScore,favTeam,favYes,favNo,t1Yes,t1No,t2Yes,t2No,drawYes,drawNo,favT1Name,favT2Name;
    ImageView currentInningTeamLogo;
    String currentInningsId, firstbattername, firstbatterruns, firstbatterpalyedballes, secondbattername,
            secondbatterruns, secondbatterpalyedballes,
            bollernam, bollerwickets, bollerbowlerOver, LCRR, LRRR;
    RecyclerView inningBattingRv, inningYetToBat, inningPartnerShips,sessionRv;
    CardView showScoreCard,favCard,oddsCard;
    String sid, mid;
    View view;
    String HOST;
    List<battingCardModal> battingCardModalList = new ArrayList<>();
    List<battingCardModal> yetToBatList = new ArrayList<>();
    List<PartnershipsModal> partnershipsModalList = new ArrayList<>();
    List<SessionsDataModel> sessionsDataModelList=new ArrayList<>();
    SessionRecyclerAdapter sessionRecyclerAdapter;
    DatabaseReference mDatabase;
    PartnershipsAdapter adapter3;
    CurrentInningBattingAdapter cadapter, cadapter2;
    String oover, oruns, owikts;
    TextView ovrs, runs, wkts;
    Chip b1, b2, b3, b4, b5, b6;
    ArrayList<OverBallScoreModel> overBallScoreModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live, container, false);
        battingCardModalList.clear();
        yetToBatList.clear();
        partnershipsModalList.clear();
        sessionsDataModelList.clear();
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        initialize();
        setData();
        showScoreCard.setOnClickListener(this);
        sid = requireActivity().getIntent().getStringExtra("sid");
        mid = requireActivity().getIntent().getStringExtra("mid");
        load();
        return view;
    }

    private void load() {
        //live scores data
        new Load().execute(HOST + "getMatchesHighlight?sid=" + Integer.parseInt(sid) + "&mid=" + Integer.parseInt(mid));
        //load ball-by-ball data
        new LoadBallbyball().execute(HOST + "getCommentary?sid=" + sid + "&mid=" + mid);
        //odds data
//        new LoadOdds().execute(HOST + "Odds?sid=" + sid + "&mid=" + mid);
        loadOddsData(sid,mid);
        }

    private void loadOddsData(String sid, String mid) {
        mDatabase = FirebaseDatabase.getInstance().getReference("odds");
        try {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(sid+"S"+mid)){
                            if(snapshot.child(sid+"S"+mid).hasChild("fav")){
                                    favTeam.setText(snapshot.child(sid+"S"+mid).child("fav").child("teamName").getValue().toString());
                                    favYes.setText(snapshot.child(sid+"S"+mid).child("fav").child("yes").getValue().toString());
                                    favNo.setText(snapshot.child(sid+"S"+mid).child("fav").child("no").getValue().toString());
                                    favCard.setVisibility(View.VISIBLE);
                            }
                            if(snapshot.child(sid+"S"+mid).hasChild("odds")){
                                    favT1Name.setText(snapshot.child(sid+"S"+mid).child("odds").child("team1").child("name").getValue().toString());
                                    t1Yes.setText(snapshot.child(sid+"S"+mid).child("odds").child("team1").child("yes").getValue().toString());
                                    t1No.setText(snapshot.child(sid+"S"+mid).child("odds").child("team1").child("no").getValue().toString());
                                    favT2Name.setText(snapshot.child(sid+"S"+mid).child("odds").child("team2").child("name").getValue().toString());
                                    t2Yes.setText(snapshot.child(sid+"S"+mid).child("odds").child("team2").child("yes").getValue().toString());
                                    t2No.setText(snapshot.child(sid+"S"+mid).child("odds").child("team2").child("no").getValue().toString());
                                    drawYes.setText(snapshot.child(sid+"S"+mid).child("odds").child("draw").child("yes").getValue().toString());
                                    drawNo.setText(snapshot.child(sid+"S"+mid).child("odds").child("draw").child("no").getValue().toString());
                                    oddsCard.setVisibility(View.VISIBLE);
                            }
                            if(snapshot.child(sid+"S"+mid).hasChild("session")){
                                int j=(int)snapshot.child(sid+"S"+mid).child("session").getChildrenCount()-1;
                                    for(int i=0;i<=j;i++){
                                            sessionsDataModelList.clear();
                                            String op = snapshot.child(sid+"S"+mid).child("session").child(String.valueOf(i)).child("op").getValue().toString();
                                            String min =snapshot.child(sid+"S"+mid).child("session").child(String.valueOf(i)).child("min").getValue().toString();
                                            String max =snapshot.child(sid+"S"+mid).child("session").child(String.valueOf(i)).child("max").getValue().toString();
                                            String yes =snapshot.child(sid+"S"+mid).child("session").child(String.valueOf(i)).child("yes").getValue().toString();
                                            String no =snapshot.child(sid+"S"+mid).child("session").child(String.valueOf(i)).child("no").getValue().toString();
                                            String overs =snapshot.child(sid+"S"+mid).child("session").child(String.valueOf(i)).child("overs").getValue().toString();
                                            String runs =snapshot.child(sid+"S"+mid).child("session").child(String.valueOf(i)).child("runs").getValue().toString();
                                            if (!op.isEmpty() || !min.isEmpty() || !max.isEmpty() || !yes.isEmpty() || !no.isEmpty() || !overs.isEmpty() || !runs.isEmpty()) {
                                                SessionsDataModel model = new SessionsDataModel(overs, yes, no, op, min, max, runs);
                                                sessionsDataModelList.add(model);
                                                sessionRecyclerAdapter.notifyDataSetChanged();
                                            }
                                    }
                                    if(j>=0){
                                        sessionRv.setVisibility(View.VISIBLE);
                                    }
                            }
                    }
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Snackbar.make(view, "Data not available!",Snackbar.LENGTH_SHORT).show();
        }
    }

    private void SetOverOverview() {
        ovrs.setText(oover);
        runs.setText(oruns);
        wkts.setText(owikts);
    }

    private int getBallColor(String isWkt) {
        return (isWkt.equalsIgnoreCase("true")) ? android.R.color.holo_red_dark : android.R.color.holo_green_dark;
    }

    private void SetOverBallScore(String ballss, String iswikt, String runss) {
        int ball = Integer.parseInt(ballss); //get current ball
        int color = getBallColor(iswikt); //check isWkt to get ball color
        AtomicReference<String> runs = new AtomicReference<>(runss); //get runs
        if (runs.get().equals("6") || runs.get().equals("4")) {
            color = android.R.color.holo_blue_dark;
        }
        if ((iswikt).equalsIgnoreCase("true")) {
            runs.set("w");
        }
        switch (ball) {
            case 1: {
                b1.setText(runs.get());
                b1.setChipBackgroundColorResource(color);
//                b2.setChipBackgroundColorResource(R.color.background);
//                b3.setChipBackgroundColorResource(R.color.background);
//                b4.setChipBackgroundColorResource(R.color.background);
//                b5.setChipBackgroundColorResource(R.color.background);
//                b6.setChipBackgroundColorResource(R.color.background);
                break;
            }
            case 2: {
                b2.setText(runs.get());
                b2.setChipBackgroundColorResource(color);
                break;
            }
            case 3: {
                b3.setText(runs.get());
                b3.setChipBackgroundColorResource(color);
                break;
            }
            case 4: {
                b4.setText(runs.get());
                b4.setChipBackgroundColorResource(color);
                break;
            }
            case 5: {
                b5.setText(runs.get());
                b5.setChipBackgroundColorResource(color);
                break;
            }
            case 6: {
                b6.setText(runs.get());
                b6.setChipBackgroundColorResource(color);
                break;
            }

        }
    }

    private void setData() {
        //RVs
        //current inning battings
        inningBattingRv.hasFixedSize();
        inningBattingRv.setLayoutManager(new LinearLayoutManager(getContext()));
        cadapter = new CurrentInningBattingAdapter(getContext(), battingCardModalList);
        inningBattingRv.setAdapter(cadapter);
        //yet to bat RV
        inningYetToBat.hasFixedSize();
        inningYetToBat.setLayoutManager(new LinearLayoutManager(getContext()));
        cadapter2 = new CurrentInningBattingAdapter(getContext(), yetToBatList);
        inningYetToBat.setAdapter(cadapter2);
        //partnerships data Rv
        inningPartnerShips.hasFixedSize();
        inningPartnerShips.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter3 = new PartnershipsAdapter(getContext(), partnershipsModalList);
        inningPartnerShips.setAdapter(adapter3);
        //session Rv data
        sessionRv.hasFixedSize();
        sessionRv.setLayoutManager(new LinearLayoutManager(getContext()));
        sessionRecyclerAdapter=new SessionRecyclerAdapter(sessionsDataModelList);
        sessionRv.setAdapter(sessionRecyclerAdapter);

    }

    //update ball-by-ball data
    private void ballByBallUpdate() {
        SetOverOverview();
        for (int i = 0; i < overBallScoreModels.size(); i++) {
            SetOverBallScore(overBallScoreModels.get(i).getBallnumber(), overBallScoreModels.get(i).getIswicket(), overBallScoreModels.get(i).getBallrun());
        }
    }
    //over overview data
    @SuppressLint("SetTextI18n")
    private void update() {
        currentInningTeamScore.setText(sscore);
        //  currentInningTeamLogo.
        if (homelogoUrl != null) {
            if (homelogoUrl.trim().length() != 0) {
                Picasso.get().load(homelogoUrl).into(currentInningTeamLogo);
            }
        }
        if (firstbattername != null) {
            currentBatsman1.setText(firstbattername);
        } else {
            currentBatsman1.setText("-");
        }
        if (secondbattername != null) {
            currentBatsman2.setText(secondbattername);
        } else {
            currentBatsman2.setText("-");
        }
        if (bollernam != null) {
            currentBowler.setText(bollernam);
        } else {
            currentBowler.setText("-");
        }
        if (firstbatterruns == null || firstbatterpalyedballes == null) {
            currentBatsman1Score.setText("N.A");
        } else {
            currentBatsman1Score.setText(firstbatterruns + "(" + firstbatterpalyedballes + ")");
        }
        if (secondbatterruns == null || secondbatterpalyedballes == null) {
            currentBatsman2Score.setText("N.A");
        } else {
            currentBatsman2Score.setText(secondbatterruns + "(" + secondbatterpalyedballes + ")");
        }
        if (bollerwickets == null || bollerbowlerOver == null) {
            currentBowlerScore.setText("N.A");
        } else {
            currentBowlerScore.setText(firstbatterruns + "(" + firstbatterpalyedballes + ")");
        }
        if (LRRR == null || LCRR == null) {
            RRR.setText(LRRR);
            CRR.setText(LCRR);
        } else if (LRRR == null) {
            RRR.setText("N.A");
        } else if (LCRR == null) {
            CRR.setText("N.A");
        }


    }
    //inning data,yet to bat
    @SuppressLint("NotifyDataSetChanged")
    private void update1() {
        cadapter.notifyDataSetChanged();
        cadapter2.notifyDataSetChanged();
        TextView t1 = view.findViewById(R.id.txt3);
        if (yetToBatList.isEmpty()) {
            t1.setVisibility(View.GONE);
        }
        currentInningTeamName.setText(iname);
    }
    //partnerships data
    @SuppressLint("NotifyDataSetChanged")
    private void update2() {
        adapter3.notifyDataSetChanged();
        TextView t2 = view.findViewById(R.id.txt4);
        if (partnershipsModalList.isEmpty()) {
            t2.setVisibility(View.GONE);
        }
    }
    //odds data update
    @SuppressLint("NotifyDataSetChanged")
    private void update3() {
        sessionRecyclerAdapter.notifyDataSetChanged();
        if (!sessionsDataModelList.isEmpty()) {
            sessionRv.setVisibility(View.VISIBLE);
        }else{
            sessionRv.setVisibility(View.GONE);
        }
    }


    private void initialize() {
        favT1Name= view.findViewById(R.id.t1name);
        favT2Name= view.findViewById(R.id.t2name);
        ovrs = view.findViewById(R.id.over);
        runs = view.findViewById(R.id.runs);
        wkts = view.findViewById(R.id.wkts);
        b1 = view.findViewById(R.id.ball1);
        b2 = view.findViewById(R.id.ball2);
        b3 = view.findViewById(R.id.ball3);
        b4 = view.findViewById(R.id.ball4);
        b5 = view.findViewById(R.id.ball5);
        b6 = view.findViewById(R.id.ball6);
        favTeam=view.findViewById(R.id.favTeam);
        favYes=view.findViewById(R.id.favYes);
        favNo=view.findViewById(R.id.favNo);

        t1Yes=view.findViewById(R.id.t1Yes);
        t1No=view.findViewById(R.id.t1No);

        t2Yes=view.findViewById(R.id.t2Yes);
        t2No=view.findViewById(R.id.t2No);
        drawYes=view.findViewById(R.id.drawYes);
        drawNo=view.findViewById(R.id.drawNo);

        favCard=view.findViewById(R.id.favCard);
        oddsCard=view.findViewById(R.id.oddsCards);
        sessionRv=view.findViewById(R.id.sessionRv);

        currentBatsman1 = view.findViewById(R.id.currentBatsman1);
        currentBatsman1Score = view.findViewById(R.id.currentBatsmanScore);
        currentBatsman2 = view.findViewById(R.id.currentBatsman2);
        currentBatsman2Score = view.findViewById(R.id.currentBatsman2Score);
        currentBowler = view.findViewById(R.id.currentBowler);
        currentBowlerScore = view.findViewById(R.id.currentBowlerScore);
        RRR = view.findViewById(R.id.rrr);
        CRR = view.findViewById(R.id.crr);
        currentInningTeamName = view.findViewById(R.id.currentTeamName);
        currentInningTeamScore = view.findViewById(R.id.currentTeamScore);
        currentInningTeamLogo = view.findViewById(R.id.currentTeamLogo);
        inningBattingRv = view.findViewById(R.id.currentInningRv);
        showScoreCard = view.findViewById(R.id.moreScores);
        inningYetToBat = view.findViewById(R.id.upcomingBatting);
        inningPartnerShips = view.findViewById(R.id.partnerships);

    }

    String sscore, homelogoUrl;

    private class LoadBallbyball extends AsyncTask<String, Integer, Long> {

        @Override
        protected Long doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray Innings = object.getJSONObject("data").getJSONObject("commentary").getJSONArray("innings");
                    JSONObject obj = Innings.getJSONObject(0);
                    String innigid = obj.getString("id");

                    JSONObject lastover = obj.getJSONArray("overs").getJSONObject(0);
                    JSONObject overSummary = lastover.getJSONObject("overSummary");
                    oover = lastover.getString("number");

                    oruns = overSummary.getString("runsConcededinOver");
                    owikts = overSummary.getString("wicketsTakeninOver");

                    JSONArray balls = lastover.getJSONArray("balls");
                    for (int j = 0; j < balls.length(); j++) {
                        JSONObject j_ball = balls.getJSONObject(j);


                        for (int i = 0; i < j_ball.getJSONArray("comments").length(); i++) {
                            String s_runs = j_ball.getJSONArray("comments").getJSONObject(i).getString("runs");
                            if (s_runs.equals("") || s_runs == null) {
                                s_runs = "0";
                            }
                            overBallScoreModels.add(new OverBallScoreModel(j_ball.getString("ballNumber"), s_runs, j_ball.getJSONArray("comments").getJSONObject(i).getString("isFallOfWicket")));


                        }


                    }


                }
            } catch (JSONException | IOException jsonException) {
                jsonException.printStackTrace();
            }
            return (long) 513;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            ballByBallUpdate();

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
                    JSONObject meta = data.getJSONObject("meta");
                    LCRR = meta.getString("currentRunRate");
                    LRRR = meta.getString("requiredRunRate");

                    currentInningsId = meta.getString("currentInningsId");
                    JSONObject matchDetail = data.getJSONObject("matchDetail");
                    JSONObject bowler = matchDetail.getJSONObject("bowler");
                    JSONArray currentBatters = matchDetail.getJSONArray("currentBatters");
                    firstbattername = currentBatters.getJSONObject(0).getString("name");
                    firstbatterruns = currentBatters.getJSONObject(0).getString("runs");
                    firstbatterpalyedballes = currentBatters.getJSONObject(0).getString("ballsFaced");
                    homelogoUrl = matchDetail.getJSONObject("matchSummary").getJSONObject("homeTeam").getString("logoUrl");
                    secondbattername = currentBatters.getJSONObject(1).getString("name");
                    secondbatterruns = currentBatters.getJSONObject(1).getString("runs");
                    secondbatterpalyedballes = currentBatters.getJSONObject(1).getString("ballsFaced");

                    bollernam = bowler.getString("name");
                    bollerwickets = bowler.getString("wickets");
                    bollerbowlerOver = bowler.getString("bowlerOver");
                    String homeScore=matchDetail.getJSONObject("matchSummary").getJSONObject("scores").getString("homeScore");
                    String homeOvers=matchDetail.getJSONObject("matchSummary").getJSONObject("scores").getString("homeOvers");
                    sscore = homeScore + "(" + homeOvers + ")";
                    //homeOvers


                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            new LoadScoreBoard().execute(HOST + "getScoreboard?sid=" + Integer.parseInt(sid) + "&mid=" + Integer.parseInt(mid));
            update();
        }
    }

    String iname;

    private class LoadScoreBoard extends AsyncTask<String, Integer, Long> {
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
                    JSONObject fullScorecard = data.getJSONObject("fullScorecard");
                    JSONObject innings = fullScorecard.getJSONArray("innings").getJSONObject(0);
                    iname = innings.getString("name");
                    JSONArray batsmen = innings.getJSONArray("batsmen");
                    for (int i = 0; i < batsmen.length(); i++) {
                        JSONObject batsman = batsmen.getJSONObject(i);
                        String playername = batsman.getString("name");
                        String playerruns = batsman.getString("runs");
                        String playerballs = batsman.getString("balls");
                        battingCardModal battingCardModal = new battingCardModal(playername, playerruns, playerballs, "", "");
                        if (playerballs.trim().equalsIgnoreCase("") || playerballs.trim().equalsIgnoreCase("0")) {
                            yetToBatList.add(battingCardModal);
                        } else {
                            battingCardModalList.add(battingCardModal);
                        }


                    }


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
            new LoadPatnerShip().execute(HOST + "getPartnerships?sid=" + Integer.parseInt(sid) + "&mid=" + Integer.parseInt(mid) + "&ining=" + currentInningsId);
            update1();
        }
    }

    private class LoadPatnerShip extends AsyncTask<String, Integer, Long> {
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
                    JSONObject data = object.getJSONObject("jsondata");
                    JSONArray partners = data.getJSONArray("partners");
                    for (int i = 0; i < partners.length(); i++) {
                        JSONObject obj = partners.getJSONObject(i);
                        JSONObject firstBatsman = obj.getJSONObject("firstBatsman");
                        JSONObject secondBatsman = obj.getJSONObject("secondBatsman");
                        String firstBatsmanname = firstBatsman.getString("name");
                        String secondBatsmanname = secondBatsman.getString("name");
                        String firstBatsmanruns = firstBatsman.getString("runs");
                        String secondBatsmanruns = secondBatsman.getString("runs");
                        String firstBatsmaballs = firstBatsman.getString("balls");
                        String secondBatsmaballs = secondBatsman.getString("balls");

                        PartnershipsModal modal = new PartnershipsModal(firstBatsmanname, secondBatsmanname, firstBatsmanruns, secondBatsmanruns, firstBatsmaballs, secondBatsmaballs);

                        partnershipsModalList.add(modal);
                    }

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

            update2();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == showScoreCard) {
        }
    }

    private class LoadOdds extends AsyncTask<String, Integer, Long> {
        @Override
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
                    if (object.getJSONArray("data").length()!=0)
                    {
                        JSONObject fav = object.getJSONArray("data").getJSONObject(0).getJSONObject("data").getJSONObject("fav");
                            if(!object.getJSONArray("jsondata").getJSONObject(0).getJSONObject("data").getString("fav").equals("null")) {
                                String tname=fav.getString("teamName");
                                favTeam.setText(tname);
                                String tyes=fav.getString("yes");
                                favYes.setText(tyes);
                                String tno=fav.getString("no");
                                favNo.setText(tno);
                                favCard.setVisibility(View.VISIBLE);
                            }else{
                                favCard.setVisibility(View.GONE);
                            }
                        JSONObject odds = object.getJSONArray("data").getJSONObject(0).getJSONObject("data").getJSONObject("odds");
                        if (!object.getJSONArray("jsondata").getJSONObject(0).getJSONObject("data").getString("odds").equals("null")) {
                            JSONObject team1 = odds.getJSONObject("team1");
                            favT1Name.setText(team1.getString("name"));
                            t1Yes.setText(team1.getString("yes"));
                            t1No.setText(team1.getString("no"));
                            JSONObject team2 = odds.getJSONObject("team2");
                            favT2Name.setText(team2.getString("name"));
                            t2Yes.setText(team2.getString("yes"));
                            t2No.setText(team2.getString("no"));
                            JSONObject draw = odds.getJSONObject("draw");
                            drawYes.setText(draw.getString("yes"));
                            drawNo.setText(draw.getString("no"));
                            oddsCard.setVisibility(View.VISIBLE);
                        } else {
                            oddsCard.setVisibility(View.GONE);
                        }
                        JSONArray session = object.getJSONArray("jsondata").getJSONObject(0).getJSONObject("data").getJSONArray("session");
                        if(session.length()!=0) {
                            for (int i = 0; i < session.length(); i++) {
                                String op = session.getJSONObject(i).getString("op");
                                String min = session.getJSONObject(i).getString("min");
                                String max = session.getJSONObject(i).getString("max");
                                String yes = session.getJSONObject(i).getString("yes");
                                String no = session.getJSONObject(i).getString("no");
                                String overs = session.getJSONObject(i).getString("overs");
                                String runs = session.getJSONObject(i).getString("runs");
                                if (!op.isEmpty() || !min.isEmpty() || !max.isEmpty() || !yes.isEmpty() || !no.isEmpty() || !overs.isEmpty() || !runs.isEmpty()) {
                                    SessionsDataModel model = new SessionsDataModel(overs, yes, no, op, min, max, runs);
                                    sessionsDataModelList.add(model);
                                }
                            }
                        }
                    }
                }
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Long aLong) {
            update3();
        }
    }

}