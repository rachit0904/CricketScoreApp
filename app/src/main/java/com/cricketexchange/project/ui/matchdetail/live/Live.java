package com.cricketexchange.project.ui.matchdetail.live;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.CurrentInningBattingAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.PartnershipsAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.PartnershipsModal;
import com.cricketexchange.project.Models.battingCardModal;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Live extends Fragment implements View.OnClickListener {
    TextView currentBatsman1, currentBatsman2, currentBatsman1Score, currentBatsman2Score, currentBowler, currentBowlerScore, RRR, CRR;
    TextView currentInningTeamName, currentInningTeamScore;
    ImageView currentInningTeamLogo;
    RecyclerView inningBattingRv, inningYetToBat, inningPartnerShips;
    CardView showScoreCard;
    String sid,mid;
    View view;
    List<battingCardModal> battingCardModalList = new ArrayList<>();
    List<battingCardModal> yetToBatList = new ArrayList<>();
    List<PartnershipsModal> partnershipsModalList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live, container, false);
        battingCardModalList.clear();
        yetToBatList.clear();
        partnershipsModalList.clear();
        initialize();
        setData();
        showScoreCard.setOnClickListener(this);
        sid= requireActivity().getIntent().getStringExtra("sid");
        mid=requireActivity().getIntent().getStringExtra("mid");
        load();
        return view;
    }

    private void load() {
        new Load().execute(Constants.HOST + "getMatchesHighlight?sid="+Integer.parseInt(sid)+"&mid="+Integer.parseInt(mid));
    }

    PartnershipsAdapter adapter3;

    private void setData() {
        //batsman1 batsman2 bowler name & scores
        //CRR RRR
        //Current inning team name,score and logo

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

    }

    String currentInningsId, firstbattername, firstbatterruns, firstbatterpalyedballes, secondbattername,
            secondbatterruns, secondbatterpalyedballes,
            bollernam, bollerwickets, bollerbowlerOver, LCRR, LRRR;


    @SuppressLint("SetTextI18n")
    private void update() {
        currentInningTeamScore.setText(sscore);
        //  currentInningTeamLogo.
        if (homelogoUrl!=null){
            if (homelogoUrl.trim().length() != 0) {
                Picasso.get().load(homelogoUrl).into(currentInningTeamLogo);
            }
        }

        currentBatsman1.setText(firstbattername);
        currentBatsman2.setText(secondbattername);
        currentBowler.setText(bollernam);
        currentBatsman1Score.setText(firstbatterruns + "(" + firstbatterpalyedballes + ")");
        currentBatsman2Score.setText(secondbatterruns + "(" + secondbatterpalyedballes + ")");
        currentBowlerScore.setText(bollerbowlerOver + "-" + bollerwickets);
        RRR.setText(LRRR);
        CRR.setText(LCRR);

    }

    CurrentInningBattingAdapter cadapter, cadapter2;

    @SuppressLint("NotifyDataSetChanged")
    private void update1() {
        cadapter.notifyDataSetChanged();
        cadapter2.notifyDataSetChanged();
        currentInningTeamName.setText(iname);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void update2() {
        adapter3.notifyDataSetChanged();
    }


    private void initialize() {
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
                    sscore = matchDetail.getJSONObject("matchSummary").getJSONObject("scores").getJSONObject("homeScore") + "(" + bowler.getJSONObject("matchSummary").getJSONObject("scores").getJSONObject("homeOvers") + ")";
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
            new LoadScoreBoard().execute(Constants.HOST + "getScoreboard?sid="+Integer.parseInt(sid)+"&mid="+Integer.parseInt(mid));
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
            new LoadPatnerShip().execute(Constants.HOST + "getPartnerships?sid="+Integer.parseInt(sid)+"&mid="+Integer.parseInt(mid)+"&ining="+currentInningsId);
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
                    JSONObject data = object.getJSONObject("data");
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
}