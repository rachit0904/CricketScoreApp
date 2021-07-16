package com.cricketexchange.project.ui.matchdetail.scores;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
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
    String HOST;
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
        InningDataList.clear();
        sid = requireActivity().getIntent().getStringExtra("sid");
        mid = requireActivity().getIntent().getStringExtra("mid");
        HOST = requireActivity().getIntent().getStringExtra("HOST");
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
                if(!getFallOfWickets(id).isEmpty()) {
                    wicketsRv.setAdapter(wicketsAdapter);
                }else{
                    TableLayout wkttble=view.findViewById(R.id.wktTble);
                    TextView wktTitle=view.findViewById(R.id.wicketsTitle);
                    wktTitle.setVisibility(View.GONE);
                    wkttble.setVisibility(View.GONE);
                    wicketsRv.setVisibility(View.GONE);
                }
                if(!getBowlingData(id).isEmpty()) {
                    bowlingRv.setAdapter(adapter2);
                }else{
                    TableLayout balltble=view.findViewById(R.id.bowlingInningTable);
                    TextView ballTitle=view.findViewById(R.id.bowlingTitle);
                    ballTitle.setVisibility(View.GONE);
                    balltble.setVisibility(View.GONE);
                    bowlingRv.setVisibility(View.GONE);
                }
                if(!getBattingData(id).isEmpty()) {
                    battingRv.setAdapter(adapter);
                }else{
                    TableLayout battble=view.findViewById(R.id.battingInningTable);
                    TextView batTitle=view.findViewById(R.id.battingTitle);
                    batTitle.setVisibility(View.GONE);
                    battble.setVisibility(View.GONE);
                    battingRv.setVisibility(View.GONE);
                }
            }
        }
    }

    private List<WicketsFallModel> getFallOfWickets(int id) {
        InningModal modal = InningDataList.get(id);
        return modal.getWicketsFallModelList();
    }

    private List<BattingInningModal> getBowlingData(int id) {
        InningModal modal = InningDataList.get(id);

        return modal.getBowlingInningModalList();
    }

    private List<BattingInningModal> getBattingData(int id) {
        InningModal modal = InningDataList.get(id);
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
        loadData(sid,mid);
    }

    private void loadData(String sid, String mid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scorecards").child(sid + "S" + mid).child("jsondata").child("fullScorecard")
                .child("innings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                try {
                    InningDataList.clear();
                    inningsTab.removeAllTabs();
                    for (int i = (int)snapshot.getChildrenCount()-1;i>=0 ;i--) {
                        String innNme = snapshot.child(String.valueOf(i)).child("shortName").getValue().toString();
                        String score = snapshot.child(String.valueOf(i)).child("run").getValue().toString() + " - " + snapshot.child(String.valueOf(i)).child("wicket").getValue().toString()
                                + " (" + snapshot.child(String.valueOf(i)).child("over").getValue().toString() + ")";
                        List<BattingInningModal> batsmenList = new ArrayList<>();
                        List<BattingInningModal> bowlersList = new ArrayList<>();
                        List<WicketsFallModel> wicketsList = new ArrayList<>();
                        batsmenList.clear();
                        wicketsList.clear();
                        bowlersList.clear();

                        DataSnapshot ds = snapshot.child(String.valueOf(i)).child("batsmen");
                        for (int j = 0; j < ds.getChildrenCount(); j++) {
                            String name = ds.child(String.valueOf(j)).child("name").getValue().toString();
                            String runs = ds.child(String.valueOf(j)).child("runs").getValue().toString();
                            String ball = ds.child(String.valueOf(j)).child("balls").getValue().toString();
                            String four = ds.child(String.valueOf(j)).child("fours").getValue().toString();
                            String six = ds.child(String.valueOf(j)).child("sixes").getValue().toString();
                            String sr = ds.child(String.valueOf(j)).child("strikeRate").getValue().toString();
                            String fow = ds.child(String.valueOf(j)).child("fowOrder").getValue().toString();
                            String howOut=ds.child(String.valueOf(j)).child("howOut").getValue().toString();
                            if (Integer.parseInt(fow) > 0) {
                                WicketsFallModel wicketsFallModel = new WicketsFallModel(name,
                                        ds.child(String.valueOf(j)).child("fallOfWicket").getValue().toString(),
                                        ds.child(String.valueOf(j)).child("fallOfWicketOver").getValue().toString());
                                wicketsList.add(wicketsFallModel);
                            }
                            BattingInningModal battingInningModal = new BattingInningModal(
                                    name, howOut, runs, ball, four, six, sr
                            );
                            batsmenList.add(battingInningModal);
                        }

                        DataSnapshot ds2 = snapshot.child(String.valueOf(i)).child("bowlers");

                        for (int k = 0; k < ds2.getChildrenCount(); k++) {
                            String name = ds2.child(String.valueOf(k)).child("name").getValue().toString();
                            String runs = ds2.child(String.valueOf(k)).child("runsConceded").getValue().toString();
                            String ball = ds2.child(String.valueOf(k)).child("wickets").getValue().toString();
                            String four = ds2.child(String.valueOf(k)).child("wides").getValue().toString();
                            String six = ds2.child(String.valueOf(k)).child("noBalls").getValue().toString();
                            String sr = ds2.child(String.valueOf(k)).child("economy").getValue().toString();
                            BattingInningModal ballingInningModal = new BattingInningModal(
                                    name, "", runs, ball, four, six, sr
                            );
                            bowlersList.add(ballingInningModal);
                        }

                        InningModal inningModal = new InningModal(innNme, score, batsmenList, bowlersList, wicketsList);
                        InningDataList.add(inningModal);
                    }
                    update();
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void update() {
        {
            for(int i=0;i<InningDataList.size();i++){
                inningsTab.addTab(inningsTab.newTab());
            }
        }
        for (int i = 0; i < inningsTab.getTabCount(); i++) {
            try {
                InningModal modal = InningDataList.get(i);
                inningsTab.selectTab(inningsTab.getTabAt(i).setText(modal.getInningName()));
            } catch (IndexOutOfBoundsException e) {
                Log.d("IndexOutOfBounds", "Exception: (ln 150)" + e);
            }

        }

        try {
            inningsTab.selectTab(inningsTab.getTabAt(0));
            InningModal modal2 = InningDataList.get(0);
            setscore(modal2.getTotalScore());
            inningData(0);
        } catch (IndexOutOfBoundsException e) {
            Log.d("IndexOutOfBounds", "Exception:(ln 161) " + e);
        }
    }

}