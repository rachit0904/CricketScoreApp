package com.cricketexchange.project.ui.matchdetail.scores;

import android.os.AsyncTask;
import android.os.Bundle;
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
import com.cricketexchange.project.Models.InningModal;
import com.cricketexchange.project.Models.WicketsFallModel;
import com.cricketexchange.project.R;
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
    String sid, mid;
    List<InningModal> InningDataList = new ArrayList<>();
    List<BattingInningModal> battingInningModalList1 = new ArrayList<>();
    List<BattingInningModal> bowlingInningModalList1 = new ArrayList<>();
    List<WicketsFallModel> wicketsFallModelList1 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_scores, container, false);
        battingInningModalList1.clear();
        bowlingInningModalList1.clear();
        wicketsFallModelList1.clear();
        sid = requireActivity().getIntent().getStringExtra("sid");
        mid = requireActivity().getIntent().getStringExtra("mid");
        initialize();
        load();
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
        return view;
    }

    public void setscore(String score) {
        totalScore.setText(score);
    }

    private void inningData(int id) {
        {
            //set current team's score
            InningModal modal = InningDataList.get(id);
            setscore(modal.getTotalScore());
            battingInningModalList1.clear();
            bowlingInningModalList1.clear();
            wicketsFallModelList1.clear();
            //get RVs data for inning-id
            battingRv.hasFixedSize();
            bowlingRv.hasFixedSize();
            wicketsRv.hasFixedSize();
            bowlingRv.setLayoutManager(new LinearLayoutManager(getContext()));
            battingRv.setLayoutManager(new LinearLayoutManager(getContext()));
            wicketsRv.setLayoutManager(new LinearLayoutManager(getContext()));
            {
                InningBattingBowlingAdapter adapter = new InningBattingBowlingAdapter(getContext(), getBattingData(id));
                InningBattingBowlingAdapter adapter2 = new InningBattingBowlingAdapter(getContext(), getBowlingData(id));
                WicketsAdapter wicketsAdapter = new WicketsAdapter(getContext(), getFallOfWickets(id));
                wicketsRv.setAdapter(wicketsAdapter);
                bowlingRv.setAdapter(adapter2);
                battingRv.setAdapter(adapter);
            }
        }
    }

    private List<WicketsFallModel> getFallOfWickets(int id) {
        InningModal modal=InningDataList.get(id);
        return modal.getWicketsFallModelList();
    }

    private List<BattingInningModal> getBowlingData(int id) {
        InningModal modal=InningDataList.get(id);
        return modal.getBowlingInningModalList();
    }

    private List<BattingInningModal> getBattingData(int id) {
        InningModal modal=InningDataList.get(id);
        return modal.getBattingInningModalList();
    }

    public void initialize() {
        inningsTab = view.findViewById(R.id.inningTab);
        totalScore = view.findViewById(R.id.totalScore);
        battingRv = view.findViewById(R.id.inningBatingRv);
        bowlingRv = view.findViewById(R.id.inningBowlingRv);
        wicketsRv = view.findViewById(R.id.wicketsRv);
    }

    private void load() {
        new LoadScoreBoard().execute(Constants.HOST + "getScoreboard?sid="+sid+"&mid="+mid);
    }


    private void update() {
//        if(InningDataList.size()>2){
//            for(int i=0;i<InningDataList.size()-2;i++){
//                inningsTab.addTab(inningsTab.newTab());
//            }
//        }
        for(int i=0;i<inningsTab.getTabCount();i++) {
            InningModal modal = InningDataList.get(i);
            inningsTab.selectTab(inningsTab.getTabAt(i).setText(modal.getInningName()));
        }
            inningsTab.selectTab(inningsTab.getTabAt(0));
            InningModal modal2 = InningDataList.get(0);
            setscore(modal2.getTotalScore());
            inningData(0);
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
                    if(inningss.length()>0) {
                        for (int j = inningss.length() - 1; j >= 0; j--) {
                            JSONObject innings = inningss.getJSONObject(j);
                            String inningName = innings.getString("shortName");
                            String runs = innings.getString("run");
                            String wkts = innings.getString("wicket");
                            String ovr = innings.getString("over");
                            String totalScore = runs + "-" + wkts + " (" + ovr + ")";
                            JSONArray batsmen = innings.getJSONArray("batsmen");
                            JSONArray bowlers = innings.getJSONArray("bowlers");
                            List<BattingInningModal> batsmenList = new ArrayList<>();
                            List<BattingInningModal> bowlersList = new ArrayList<>();
                            List<WicketsFallModel> wicketsList = new ArrayList<>();
                            batsmenList.clear();
                            wicketsList.clear();
                            bowlersList.clear();
                            for (int i = 0; i < batsmen.length(); i++) {
                                JSONObject batsman = batsmen.getJSONObject(i);
                                String playername = batsman.getString("name");
                                String playerruns = batsman.getString("runs");
                                String playerballs = batsman.getString("balls");
                                String playerhowOut = batsman.getString("howOut");
                                String playerstrikeRate = batsman.getString("strikeRate");
                                String playerfours = batsman.getString("fours");
                                String playersixes = batsman.getString("sixes");
                                BattingInningModal battingInningModal = new BattingInningModal(playername, playerhowOut, playerruns, playerballs, playerfours, playersixes, playerstrikeRate);
                                int fowOrder = Integer.parseInt(batsman.getString("fowOrder"));
                                if (fowOrder > 0) {
                                    String name = batsman.getString("name");
                                    String score = batsman.getString("fallOfWicket");
                                    String overs = batsman.getString("fallOfWicketOver");
                                    WicketsFallModel wicketsFallModel = new WicketsFallModel(name, score, overs);
                                    wicketsList.add(wicketsFallModel);
                                }
                                batsmenList.add(battingInningModal);
                            }
                            for (int i = 0; i < bowlers.length(); i++) {
                                JSONObject ballers = bowlers.getJSONObject(i);
                                String playername = ballers.getString("name");
                                String outBy = "";
                                String playerruns = ballers.getString("runsConceded");
                                String playerWickets = ballers.getString("wickets");
                                String wides = ballers.getString("wides");
                                String noBalls = ballers.getString("noBalls");
                                String economy = ballers.getString("economy");
                                BattingInningModal bowlingModal = new BattingInningModal(playername, outBy, playerruns, playerWickets, wides, noBalls, economy);
                                bowlersList.add(bowlingModal);
                            }
                            InningModal inningModal = new InningModal(inningName, totalScore, batsmenList, bowlersList, wicketsList);
                            InningDataList.add(inningModal);
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
            update();
        }
    }


}