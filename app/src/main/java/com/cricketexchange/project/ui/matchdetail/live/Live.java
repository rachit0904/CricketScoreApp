package com.cricketexchange.project.ui.matchdetail.live;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.CurrentInningBattingAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.PartnershipsAdapter;
import com.cricketexchange.project.Adapter.Recyclerview.SessionRecyclerAdapter;
import com.cricketexchange.project.Models.CommentaryModal;
import com.cricketexchange.project.Models.OverBallScoreModel;
import com.cricketexchange.project.Models.PartnershipsModal;
import com.cricketexchange.project.Models.SessionsDataModel;
import com.cricketexchange.project.Models.battingCardModal;
import com.cricketexchange.project.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Live extends Fragment {
    TextView currentBatsman1, currentBatsman2, currentBatsman1Score, currentBatsman2Score, currentBowler, currentBowlerScore, RRR, CRR;
    TextView currentInningTeamName, currentInningTeamScore,favTeam,favYes,favNo,t1Yes,t1No,t2Yes,t2No,drawYes,drawNo,favT1Name,favT2Name;
    ImageView currentInningTeamLogo;
    String currentInningsId, firstbattername, firstbatterruns, firstbatterpalyedballes, secondbattername,
            secondbatterruns, secondbatterpalyedballes,
            bollernam, bollerwickets, bollerbowlerOver, LCRR, LRRR;
    RecyclerView inningBattingRv, inningYetToBat, inningPartnerShips,sessionRv;
    CardView showScoreCard,favCard,oddsCard,InningCard;
    String sid, mid;
    String sscore, homelogoUrl;
    View view;
    String HOST;
    final List<battingCardModal> battingCardModalList = new ArrayList<>();
    final List<battingCardModal> yetToBatList = new ArrayList<>();
    final List<PartnershipsModal> partnershipsModalList = new ArrayList<>();
    final List<SessionsDataModel> sessionsDataModelList=new ArrayList<>();
    SessionRecyclerAdapter sessionRecyclerAdapter;
    DatabaseReference mDatabase;
    PartnershipsAdapter adapter3;
    CurrentInningBattingAdapter cadapter, cadapter2;
    String oover, oruns, owikts;
    TextView ovrs, runs, wkts;
    Chip b1, b2, b3, b4, b5, b6;
    final ArrayList<OverBallScoreModel> overBallScoreList = new ArrayList<>();

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
        sid = requireActivity().getIntent().getStringExtra("sid");
        mid = requireActivity().getIntent().getStringExtra("mid");
        setData();
        load();
        return view;
    }

    private void load() {
        loadScoresData(sid,mid);
        loadOversData(sid,mid);
        loadInningsData(sid,mid);
        loadOddsData(sid,mid);
        }

    private void loadOversData(String sid, String mid) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Commentary").child(sid + "S" + mid).child("jsondata")
                    .child("commentary").child("innings").child("0").child("overs").child("0").child("balls");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        overBallScoreList.clear();
                        for(int i =Integer.parseInt(String.valueOf(snapshot.getChildrenCount()-1)); i>=0;i--) {
                            OverBallScoreModel ballScoreModel=new OverBallScoreModel();
                            try {
                                String ballno = snapshot.child(String.valueOf(i)).child("ballNumber").getValue().toString();
                                String isFallOfWicket = snapshot.child(String.valueOf(i)).child("comments").child(String.valueOf("0")).child("isFallOfWicket").getValue().toString();
                                String ballruns = snapshot.child(String.valueOf(i)).child("comments").child(String.valueOf("0")).child("runs").getValue().toString();
                                if(!ballruns.isEmpty()) {
                                    ballScoreModel = new OverBallScoreModel(ballno, ballruns, isFallOfWicket);
                                    overBallScoreList.add(ballScoreModel);
                                }
                            }catch (Exception e){
                                String ballno = "";
                                String isFallOfWicket = "";
                                String ballruns = "";
                            }
                            try {
                                String currBatsman = snapshot.child(String.valueOf(i)).child("comments").child(String.valueOf("0")).child("batsmanName").getValue().toString();
                                currentBatsman1.setText(currBatsman);
                                String currBowler = snapshot.child(String.valueOf(i)).child("comments").child(String.valueOf("0")).child("bowlerName").getValue().toString();
                                currentBowler.setText(currBowler);
                            }catch (Exception e){
                                String currBatsman = "N.A";
                                currentBatsman1.setText(currBatsman);
                                String currBowler = "N.A.";
                                currentBowler.setText(currBowler);
                            }
                        }
                        ballByBallUpdate();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Snackbar.make(view, "Match data NA", Snackbar.LENGTH_SHORT).show();
                    }
                });
        }catch (Exception e){
            Snackbar.make(view, "Match data NA", Snackbar.LENGTH_SHORT).show();
        }
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Commentary").child(sid + "S" + mid).child("jsondata")
                    .child("commentary").child("innings").child("0").child("overs").child("0");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    try {
                        oover = snapshot.child("number").getValue().toString();
                        oruns = snapshot.child("overSummary").child("runsConcededinOver").getValue().toString();
                        owikts = snapshot.child("overSummary").child("wicketsTakeninOver").getValue().toString();
                    }catch (Exception e){
                        oover="-";
                        oruns="-";
                        owikts="-";
                    }
                    SetOverOverview();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Snackbar.make(view, "Match data NA", Snackbar.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Snackbar.make(view, "Match data NA", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void loadScoresData(String sid, String mid) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scorecards").child(sid + "S" + mid).child("jsondata").child("fullScorecard")
                    .child("innings").child(String.valueOf("0"));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    try {
                        CRR.setText(snapshot.child("runRate").getValue().toString());
                        RRR.setText(snapshot.child("requiredRunRate").getValue().toString());
                    }catch (Exception e){
                        CRR.setText("N.A.");
                        RRR.setText("N.A.");
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    CRR.setText("N.A.");
                    RRR.setText("N.A.");
                }
            });
        }catch (Exception e){
            CRR.setText("N.A.");
            RRR.setText("N.A.");
        }
    }

    private void loadInningsData(String sid, String mid) {
        try {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scorecards").child(sid + "S" + mid)
                            .child("jsondata").child("fullScorecard").child("innings").child("0");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            try {
                                String currInn = snapshot.child("name").getValue().toString();
                                currentInningTeamName.setText(currInn);
                                String currInnScore = snapshot.child("run").getValue().toString() + " (" + snapshot.child("over").getValue().toString() + ")";
                                currentInningTeamScore.setText(currInnScore);
                                DataSnapshot ds = snapshot.child("batsmen");
                                for (int i = 0; i < ds.getChildrenCount() - 1; i++) {
                                    String playername = ds.child(String.valueOf(i)).child("name").getValue().toString();
                                    String playerruns = ds.child(String.valueOf(i)).child("runs").getValue().toString();
                                    ;
                                    String playerballs = ds.child(String.valueOf(i)).child("balls").getValue().toString();
                                    ;
                                    battingCardModal battingCardModal = new battingCardModal(playername, playerruns, playerballs, sid, mid);
                                    if (playerballs.trim().equalsIgnoreCase("") || playerballs.trim().equalsIgnoreCase("0")) {
                                        yetToBatList.add(battingCardModal);
                                    } else {
                                        battingCardModalList.add(battingCardModal);
                                    }
                                }
                                update1();
                            } catch (Exception e) {
                                TextView textView=view.findViewById(R.id.txt2);
                                TextView textView2=view.findViewById(R.id.txt3);
                                textView.setVisibility(View.GONE);
                                textView2.setVisibility(View.GONE);
                                InningCard.setVisibility(View.GONE);
                                inningBattingRv.setVisibility(View.GONE);
                                inningYetToBat.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }catch (Exception e){
                }
        try {
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Patnerships").child(sid + "S" + mid ).child("jsondata")
                        .child("partners");
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (int i = 0; i < snapshot.getChildrenCount(); i++) {
                            PartnershipsModal partnershipsModal = new PartnershipsModal(
                                    snapshot.child(String.valueOf(i)).child("firstBatsman").child("name").getValue().toString(),
                                    snapshot.child(String.valueOf(i)).child("secondBatsman").child("name").getValue().toString(),
                                    snapshot.child(String.valueOf(i)).child("firstBatsman").child("runs").getValue().toString(),
                                    snapshot.child(String.valueOf(i)).child("secondBatsman").child("runs").getValue().toString(),
                                    snapshot.child(String.valueOf(i)).child("firstBatsman").child("balls").getValue().toString(),
                                    snapshot.child(String.valueOf(i)).child("secondBatsman").child("balls").getValue().toString());
                            partnershipsModalList.add(partnershipsModal);
                        }
                        update2();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }catch (Exception e){
            }
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
        LinearLayout overviewCard=view.findViewById(R.id.overScore);
        if(oover.equalsIgnoreCase("-")  && oruns.equalsIgnoreCase("-") && owikts.equalsIgnoreCase("-")  ){
            overviewCard.setVisibility(View.GONE);
        }else {
            ovrs.setText(oover);
            runs.setText(oruns);
            wkts.setText(owikts);
        }
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
        ChipGroup chipGroup=view.findViewById(R.id.overScoreGroup);
        if(overBallScoreList.isEmpty()){
            chipGroup.setVisibility(View.GONE);
        }
        for (int i = 0; i < overBallScoreList.size(); i++) {
            SetOverBallScore(overBallScoreList.get(i).getBallnumber(), overBallScoreList.get(i).getIswicket(), overBallScoreList.get(i).getBallrun());
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
    }
    //partnerships data
    @SuppressLint("NotifyDataSetChanged")
    private void update2() {
        TextView t2 = view.findViewById(R.id.txt4);
        if (partnershipsModalList.isEmpty()) {
            t2.setVisibility(View.GONE);
        }
        adapter3.notifyDataSetChanged();
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
        InningCard=view.findViewById(R.id.overViewCard);
        currentInningTeamName = view.findViewById(R.id.currentTeamName);
        currentInningTeamScore = view.findViewById(R.id.currentTeamScore);
        currentInningTeamLogo = view.findViewById(R.id.currentTeamLogo);
        inningBattingRv = view.findViewById(R.id.currentInningRv);
        showScoreCard = view.findViewById(R.id.moreScores);
        inningYetToBat = view.findViewById(R.id.upcomingBatting);
        inningPartnerShips = view.findViewById(R.id.partnerships);

    }

}
