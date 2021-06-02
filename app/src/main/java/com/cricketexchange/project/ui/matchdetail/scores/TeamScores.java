package com.cricketexchange.project.ui.matchdetail.scores;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.InningBattingBowlingAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.WicketsAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.BattingInningModal;
import com.cricketexchange.project.Models.WicketsFallModel;
import com.cricketexchange.project.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TeamScores extends Fragment {
    TabLayout inningsTab;
    TextView totalScore;
    RecyclerView battingRv;
    RecyclerView bowlingRv;
    RecyclerView wicketsRv;
    View view;
    TabItem t1Item, t2Item;
    List<BattingInningModal> battingInningModalList1 = new ArrayList<>();
    List<BattingInningModal> bowlingInningModalList1 = new ArrayList<>();
    List<WicketsFallModel> wicketsFallModelList1 = new ArrayList<>();

    List<BattingInningModal> battingInningModalList2 = new ArrayList<>();
    List<BattingInningModal> bowlingInningModalList2 = new ArrayList<>();
    List<WicketsFallModel> wicketsFallModelList2 = new ArrayList<>();

    List<BattingInningModal> battingInningModalList3 = new ArrayList<>();
    List<BattingInningModal> bowlingInningModalList3 = new ArrayList<>();
    List<WicketsFallModel> wicketsFallModelList3 = new ArrayList<>();
    int inningcount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_scores, container, false);
//        wicketsFallModelList.clear();
//        bowlingInningModalList.clear();
//        battingInningModalList.clear();
        initialize();
        //get current inning id
        //if inning id=3 -> super over
//        t1Item = view.findViewById(R.id.t1InningsTab);
//        t2Item = view.findViewById(R.id.t2InningsTab);
//        t2Item.set
        inningData(0);

        if (inningcount > 2) {
            inningsTab.addTab(inningsTab.newTab().setText("Super Over"));
        }
        //set values default for current inn

        inningsTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                inningData(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        load();
        return view;
    }

    InningBattingBowlingAdapter adapter, adapter2;
    WicketsAdapter wicketsAdapter;

    private void inningData(int id) {
        //set current team's score
        //get RVs data for inning-id


        battingRv.hasFixedSize();
        bowlingRv.hasFixedSize();
        wicketsRv.hasFixedSize();
        bowlingRv.setLayoutManager(new LinearLayoutManager(getContext()));
        battingRv.setLayoutManager(new LinearLayoutManager(getContext()));
        wicketsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        if (id == 0) {
            InningBattingBowlingAdapter adapter = new InningBattingBowlingAdapter(getContext(), battingInningModalList1);
            InningBattingBowlingAdapter adapter2 = new InningBattingBowlingAdapter(getContext(), bowlingInningModalList1);
            WicketsAdapter wicketsAdapter = new WicketsAdapter(getContext(), wicketsFallModelList1);
            wicketsRv.setAdapter(wicketsAdapter);
            bowlingRv.setAdapter(adapter2);
            battingRv.setAdapter(adapter);
        } else if (id == 1) {
            InningBattingBowlingAdapter adapter = new InningBattingBowlingAdapter(getContext(), battingInningModalList2);
            InningBattingBowlingAdapter adapter2 = new InningBattingBowlingAdapter(getContext(), bowlingInningModalList2);
            WicketsAdapter wicketsAdapter = new WicketsAdapter(getContext(), wicketsFallModelList2);

            bowlingRv.setAdapter(adapter2);
            wicketsRv.setAdapter(wicketsAdapter);
            battingRv.setAdapter(adapter);
        } else {
            InningBattingBowlingAdapter adapter = new InningBattingBowlingAdapter(getContext(), battingInningModalList3);
            InningBattingBowlingAdapter adapter2 = new InningBattingBowlingAdapter(getContext(), bowlingInningModalList3);
            WicketsAdapter wicketsAdapter = new WicketsAdapter(getContext(), wicketsFallModelList3);
            wicketsRv.setAdapter(wicketsAdapter);
            battingRv.setAdapter(adapter);
            bowlingRv.setAdapter(adapter2);
        }


    }

    private void getWktFallData(int id, String name, String score, String over) {
        //for dynamic data use id

        WicketsFallModel model = new WicketsFallModel(name, score, over);
        if (id == 0) {

            wicketsFallModelList1.add(model);

        } else if (id == 1) {

            wicketsFallModelList2.add(model);

        } else {

            wicketsFallModelList3.add(model);

        }


    }

    private void getBowlingData(int id, String a, String b, String c, String d, String e, String f) {
        //for dynamic data use id

        //For Bowler pass ->(Bowler Name,runs,wkt,wide,noBall,economy);
        BattingInningModal model = new BattingInningModal(a, "", b, c, d, e, f);
        if (id == 0) {

            bowlingInningModalList1.add(model);

        } else if (id == 1) {

            bowlingInningModalList2.add(model);

        } else {

            bowlingInningModalList3.add(model);

        }


    }

    private void getBattingData(int id, String a, String b, String c, String d, String e, String f, String g) {
        //for dynamic data use id


        BattingInningModal model = new BattingInningModal(a, b, c, d, e, f, g);
        if (id == 0) {

            battingInningModalList1.add(model);

        } else if (id == 1) {

            battingInningModalList2.add(model);

        } else {

            battingInningModalList3.add(model);

        }
    }


    public void initialize() {

        inningsTab = view.findViewById(R.id.inningTab);

        totalScore = view.findViewById(R.id.totalScore);
        battingRv = view.findViewById(R.id.inningBatingRv);
        bowlingRv = view.findViewById(R.id.inningBowlingRv);
        wicketsRv = view.findViewById(R.id.wicketsRv);
    }

    private void load() {
        new Load().execute(Constants.HOST + "getMatchesHighlight?sid=2796&mid=51038");
    }

    @SuppressLint("NotifyDataSetChanged")
    private void update1() {
        for (int i = 0; i < inningcount; i++) {
            inningData(i);
        }

//        adapter.notifyDataSetChanged();
//        adapter2.notifyDataSetChanged();
//        wicketsAdapter.notifyDataSetChanged();
    }

    private void update() {
        totalScore.setText(sscore);

    }

    String homeTeam, awayTeam, sscore;

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


                    JSONObject matchDetail = data.getJSONObject("matchDetail");
                    JSONObject bowler = matchDetail.getJSONObject("bowler");
                    JSONArray currentBatters = matchDetail.getJSONArray("currentBatters");
                    homeTeam = matchDetail.getJSONObject("matchSummary").getJSONObject("homeTeam").getString("name");
                    awayTeam = matchDetail.getJSONObject("matchSummary").getJSONObject("awayTeam").getString("logoUrl");
                    sscore = matchDetail.getJSONObject("matchSummary").getJSONObject("scores").getString("homeScore") + "(" + matchDetail.getJSONObject("matchSummary").getJSONObject("scores").getString("homeOvers") + ")";
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
            new LoadScoreBoard().execute(Constants.HOST + "getScoreboard?sid=2796&mid=51038");
            update();
        }
    }

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
                    JSONArray inningss = fullScorecard.getJSONArray("innings");
                    for (int j = inningss.length() - 1; j > -1; j--) {
                        Log.e("FOR", "J : " + j);
                        JSONObject innings = inningss.getJSONObject(j);
                        inningcount = innings.length();
                        JSONArray batsmen = innings.getJSONArray("batsmen");
                        JSONArray bowlers = innings.getJSONArray("bowlers");
                        for (int i = 0; i < batsmen.length(); i++) {
                            JSONObject batsman = batsmen.getJSONObject(i);
                            String playername = batsman.getString("name");
                            String playerruns = batsman.getString("runs");
                            String playerballs = batsman.getString("balls");
                            String playerhowOut = batsman.getString("runs");
                            String playerstrikeRate = batsman.getString("strikeRate");
                            String playerfours = batsman.getString("fours");
                            String playersixes = batsman.getString("sixes");
                            String playerfallOfWicketOver;
                            //String playersfallOfWicket = batsman.getString("fallOfWicket");
                            try {
                                playerfallOfWicketOver = batsman.getString("fallOfWicketOver");

                            } catch (JSONException er) {
                                playerfallOfWicketOver = "0";
                            }
                            String playerfowOrder = batsman.getString("fowOrder");
                            BattingInningModal battingInningModal = new BattingInningModal(playername, playerhowOut, playerruns, playerballs, playerfours, playersixes, playerstrikeRate);
                            if (j == 0) {
                                Log.e("IF BAT ", "0");
                                battingInningModalList1.add(battingInningModal);
                                if (!playerfowOrder.trim().equalsIgnoreCase("0")) {
                                    WicketsFallModel model = new WicketsFallModel(playername, playerruns, playerfallOfWicketOver);
                                    wicketsFallModelList1.add(model);
                                }

                            } else if (j == 1) {
                                Log.e("IF BAT ", "1");
                                battingInningModalList2.add(battingInningModal);
                                if (!playerfowOrder.trim().equalsIgnoreCase("0")) {
                                    WicketsFallModel model = new WicketsFallModel(playername, playerruns, playerfallOfWicketOver);
                                    wicketsFallModelList2.add(model);
                                }

                            } else {
                                Log.e("IF BAT ", "3");
                                battingInningModalList3.add(battingInningModal);
                                if (!playerfowOrder.trim().equalsIgnoreCase("0")) {
                                    WicketsFallModel model = new WicketsFallModel(playername, playerruns, playerfallOfWicketOver);
                                    wicketsFallModelList3.add(model);
                                }

                            }


                        }
                        for (int i = 0; i < bowlers.length(); i++) {
                            JSONObject bowler = bowlers.getJSONObject(i);
                            String playername = bowler.getString("name");
                            String playerruns = bowler.getString("runsConceded");
                            String playerwides = bowler.getString("wides");
                            String playerwickets = bowler.getString("wickets");
                            String playernoBalls = bowler.getString("noBalls");
                            String playereconomy = bowler.getString("economy");
                            BattingInningModal battingCardModal = new BattingInningModal(playername, "", playerruns, playerwickets, playerwides, playernoBalls, playereconomy);


                            if (j == 0) {
                                Log.e("IF BALL ", "0");
                                bowlingInningModalList1.add(battingCardModal);

                            } else if (j == 1) {
                                Log.e("IF BALL ", "1");
                                bowlingInningModalList2.add(battingCardModal);

                            } else {
                                Log.e("IF BALL ", "2");
                                bowlingInningModalList3.add(battingCardModal);

                            }


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

            update1();
        }
    }


}