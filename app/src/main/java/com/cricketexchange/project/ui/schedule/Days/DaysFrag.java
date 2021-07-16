package com.cricketexchange.project.ui.schedule.Days;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DaysFrag extends Fragment implements View.OnClickListener {
    private String HOST = "";
    RecyclerView recyclerView;
    List<MatchesModel> modelList = new ArrayList<>();
    SharedPreferences preferences;
    List<MatchesChildModel> childModelList = new ArrayList<>();
    List<MatchesChildModel> filterdchildModelList = new ArrayList<>();
    ChipGroup tours;
    MatchesAdapter adapter;
    List<String> series =new ArrayList<>();
    View view;
    Chip all, test, t20, odi, international, league, women;
    Set<Date> dates = new TreeSet<>();
    SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_days, container, false);
        preferences = requireActivity().getSharedPreferences(Constants.Filter, 0);
        recyclerView = view.findViewById(R.id.days);
        progressBar = view.findViewById(R.id.progressBar);
//        recyclerView.hasFixedSize();
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        dates.clear();
        filterdchildModelList.clear();
        childModelList.clear();
        modelList.clear();
        tours = view.findViewById(R.id.tours);
        tours.setVisibility(View.GONE);
        all = view.findViewById(R.id.all);
        t20 = view.findViewById(R.id.t20);
        odi = view.findViewById(R.id.odi);
        test = view.findViewById(R.id.test);
        league = view.findViewById(R.id.league);
        women = view.findViewById(R.id.women);
        international = view.findViewById(R.id.international);
        all.setChecked(true);
        all.setChipBackgroundColorResource(android.R.color.holo_green_dark);
        all.setCloseIconEnabled(true);
        all.setOnClickListener(this);
        test.setOnClickListener(this);
        odi.setOnClickListener(this);
        t20.setOnClickListener(this);
        league.setOnClickListener(this);
        international.setOnClickListener(this);
        women.setOnClickListener(this);
        series.clear();
        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        loadAllSeries();
        loadSeriesMatches();
    }

    private void loadAllSeries() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Series");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(int i=0;i<snapshot.child("1").child("jsondata").child("seriesIdList").getChildrenCount();i++){
                    series.add(snapshot.child("1").child("jsondata").child("seriesIdList").child(String.valueOf(i)).getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadSeriesMatches() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Matches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                childModelList.clear();
                for(String sid : series){
                    DataSnapshot dataSnapshot = snapshot.child(sid).child("jsondata").child("matchList").child("matches");
                    int i;
                    for ( i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        MatchesChildModel matchesChildModel = new MatchesChildModel();
                        matchesChildModel.setsId(sid);
                        String mid = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("id").getValue());
                        matchesChildModel.setmId(mid);
                        matchesChildModel.setPremiure(dataSnapshot.child(String.valueOf(i)).child("series").child("name").getValue().toString());//series name
                        matchesChildModel.setStatus(dataSnapshot.child(String.valueOf(i)).child("status").getValue().toString());//currentMatchState
                        matchesChildModel.setIsDraw(dataSnapshot.child(String.valueOf(i)).child("isMatchDrawn").getValue().toString());//status upcomming mandatory//currentMatchState
                        matchesChildModel.setTeam1(dataSnapshot.child(String.valueOf(i)).child("homeTeam").child("shortName").getValue().toString());
                        matchesChildModel.setTeam2(dataSnapshot.child(String.valueOf(i)).child("awayTeam").child("shortName").getValue().toString());
                        matchesChildModel.setIsmultiday(dataSnapshot.child(String.valueOf(i)).child("isMultiDay").getValue().toString());
                        matchesChildModel.setIswomen(dataSnapshot.child(String.valueOf(i)).child("isWomensMatch").getValue().toString());
                        try {
                            matchesChildModel.setType(dataSnapshot.child(String.valueOf(i)).child("cmsMatchType").getValue().toString());
                        }catch (Exception e){
                            matchesChildModel.setType("null");
                        }
                        String team1id = (dataSnapshot.child(String.valueOf(i)).child("homeTeam").child("id").getValue().toString());
                        String team2id = (dataSnapshot.child(String.valueOf(i)).child("awayTeam").child("id").getValue().toString());
//                        //in case of match
                        {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MatchesHighlight");
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    try {
                                        String winnigteamid = snapshot.child(sid + "S" + mid).child("jsondata").child("matchDetail").child("matchSummary").child("winningTeamId").getValue().toString();
                                        if (winnigteamid.equals(team1id)) {
                                            matchesChildModel.setWinTeamName(matchesChildModel.getTeam1());
                                        }
                                        else if (winnigteamid.equals(team2id)) {
                                            matchesChildModel.setWinTeamName(matchesChildModel.getTeam2());
                                        }else{
                                            matchesChildModel.setWinTeamName("N.A");
                                        }
                                    }catch (Exception e){

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }
                        try{
                            String logourl1 = dataSnapshot.child(String.valueOf(i)).child("homeTeam").child("logoUrl").getValue().toString();
                            String logourl2 = dataSnapshot.child(String.valueOf(i)).child("awayTeam").child("logoUrl").getValue().toString();
                            if(!logourl1.isEmpty() || !logourl2.isEmpty()) {
                                matchesChildModel.setTeam1Url(logourl1);
                                matchesChildModel.setTeam2Url(logourl2);
                            }else{
                                matchesChildModel.setTeam1Url("");
                                matchesChildModel.setTeam2Url("");
                            }
                        }catch (Exception e){
                            matchesChildModel.setTeam1Url("");
                            matchesChildModel.setTeam2Url("");
                        }
                        matchesChildModel.setMatchSummery(dataSnapshot.child(String.valueOf(i)).child("matchSummaryText").getValue().toString());
                        try {
                            matchesChildModel.setTeam1score(dataSnapshot.child(String.valueOf(i)).child("scores").child("homeScore").getValue().toString());
                            matchesChildModel.setTeam1over(dataSnapshot.child(String.valueOf(i)).child("scores").child("homeOvers").getValue().toString());
                            matchesChildModel.setTeam2score(dataSnapshot.child(String.valueOf(i)).child("scores").child("awayScore").getValue().toString());
                            matchesChildModel.setTeam2over(dataSnapshot.child(String.valueOf(i)).child("scores").child("awayOvers").getValue().toString());
                        }catch (Exception e){
                            matchesChildModel.setTeam1score("-");
                            matchesChildModel.setTeam1over("-");
                            matchesChildModel.setTeam2score("-");
                            matchesChildModel.setTeam2over("-");
                        }
                        String dateTime = dataSnapshot.child(String.valueOf(i)).child("startDateTime").getValue().toString();
                        String[] arr = dateTime.split("T");//arr[0] gives start date
                        String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
                        //add data to parent and child list
                        String date[] = arr[0].split("-");
                        String sD = (date[2] + "-" + date[1] + "-" + date[0]);
                        SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
                        Date d = null;
                        try {
                            d = sobj.parse(sD);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dates.add(d);
                        matchesChildModel.setStartDate(sD);
                        matchesChildModel.setStartTime(arr2[0].split(":")[0] + ":" + arr2[0].split(":")[1]);
                         if(matchesChildModel.getStatus().equalsIgnoreCase("UPCOMING")){
                            childModelList.add(matchesChildModel);
                        }
                    }
                }
                update();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



    @SuppressLint("NotifyDataSetChanged")
    private void filter(String type) {
        progressBar.setVisibility(View.VISIBLE);
        filterdchildModelList.clear();
        switch (type) {
            case "odi":
                for (int i = 0; i < childModelList.size(); i++) {
                    if (childModelList.get(i).getType().toLowerCase(Locale.ROOT).contains("odi")) {
                        filterdchildModelList.add(childModelList.get(i));
                    } else if (childModelList.get(i).getName().toLowerCase(Locale.ROOT).contains("odi")) {
                        filterdchildModelList.add(childModelList.get(i));
                    }
                }
                break;
            case "t20":
                for (int i = 0; i < childModelList.size(); i++) {
                    if (childModelList.get(i).getType().toLowerCase(Locale.ROOT).contains("t20")) {
                        filterdchildModelList.add(childModelList.get(i));
                    } else if (childModelList.get(i).getName().toLowerCase(Locale.ROOT).contains("t20")) {
                        filterdchildModelList.add(childModelList.get(i));
                    }
                }
                break;
            case "test":
                for (int i = 0; i < childModelList.size(); i++) {
                    if (childModelList.get(i).getType().toLowerCase(Locale.ROOT).contains("test")) {
                        filterdchildModelList.add(childModelList.get(i));
                    } else if (childModelList.get(i).getName().toLowerCase(Locale.ROOT).contains("test")) {
                        filterdchildModelList.add(childModelList.get(i));
                    }
                }
                break;
            case "international":
                for (int i = 0; i < childModelList.size(); i++) {
                    if (childModelList.get(i).getType().toLowerCase().contains("intl")) {
                        filterdchildModelList.add(childModelList.get(i));
                    } else if (childModelList.get(i).getName().toLowerCase(Locale.ROOT).contains("intern")) {
                        filterdchildModelList.add(childModelList.get(i));
                    }
                }
                break;
            case "league":
                for (int i = 0; i < childModelList.size(); i++) {
                    if (childModelList.get(i).getIsmultiday().toLowerCase(Locale.ROOT).contains("true")) {
                        filterdchildModelList.add(childModelList.get(i));
                    }
                }
                break;
            case "women":
                for (int i = 0; i < childModelList.size(); i++) {
                    if (childModelList.get(i).getIswomen().toLowerCase(Locale.ROOT).contains("true")) {
                        filterdchildModelList.add(childModelList.get(i));
                    }
                }
                break;
            default:
                filterdchildModelList.addAll(childModelList);

        }
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }


    private void setParentData() {
        for (Date x : dates) {
            MatchesModel model = new MatchesModel();
            model.setDate(sobj.format(x));
            modelList.add(model);
        }
    }

    private void update() {
//        tours.setVisibility(View.VISIBLE);
        recyclerView.hasFixedSize();
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        setParentData();
        adapter = new MatchesAdapter(getContext(), modelList, filterdchildModelList);
        adapter.setHOST(HOST);
        recyclerView.setAdapter(adapter);
        filterdchildModelList.addAll(childModelList);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == all) {
            if (all.isChecked()) {
                filter("all");
                all.setCloseIconEnabled(true);
                test.setCloseIconEnabled(false);
                t20.setCloseIconEnabled(false);
                odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);
                international.setCloseIconEnabled(false);
                women.setCloseIconEnabled(false);
                all.setChipBackgroundColorResource(android.R.color.holo_green_dark);
                t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);
                test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);
                international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChecked(true);
                test.setChecked(false);
                t20.setChecked(false);
                odi.setChecked(false);
                league.setChecked(false);
                international.setChecked(false);
                women.setChecked(false);
            }
        }
        if (v == odi) {
            if (odi.isChecked()) {
                filter("test");
                odi.setCloseIconEnabled(true);
                test.setCloseIconEnabled(false);
                t20.setCloseIconEnabled(false);
                all.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);
                international.setCloseIconEnabled(false);
                women.setCloseIconEnabled(false);
                odi.setChipBackgroundColorResource(android.R.color.holo_green_dark);
                t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);
                international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChecked(true);
                test.setChecked(false);
                t20.setChecked(false);
                all.setChecked(false);
                league.setChecked(false);
                international.setChecked(false);
                women.setChecked(false);
            }
        }
        if (v == t20) {

            if (t20.isChecked()) {
                filter("test");
                t20.setCloseIconEnabled(true);
                test.setCloseIconEnabled(false);
                all.setCloseIconEnabled(false);
                odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);
                international.setCloseIconEnabled(false);
                women.setCloseIconEnabled(false);
                t20.setChipBackgroundColorResource(android.R.color.holo_green_dark);
                all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);
                test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);
                international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                t20.setChecked(true);
                test.setChecked(false);
                all.setChecked(false);
                odi.setChecked(false);
                league.setChecked(false);
                international.setChecked(false);
                women.setChecked(false);
            }
        }
        if (v == test) {
            if (test.isChecked()) {
                filter("test");
                test.setCloseIconEnabled(true);
                all.setCloseIconEnabled(false);
                t20.setCloseIconEnabled(false);
                odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);
                international.setCloseIconEnabled(false);
                women.setCloseIconEnabled(false);
                test.setChipBackgroundColorResource(android.R.color.holo_green_dark);
                t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);
                international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                test.setChecked(true);
                all.setChecked(false);
                t20.setChecked(false);
                odi.setChecked(false);
                league.setChecked(false);
                international.setChecked(false);
                women.setChecked(false);
            }
        }
        if (v == international) {
            if (international.isChecked())
                filter("league");
            international.setCloseIconEnabled(true);
            test.setCloseIconEnabled(false);
            t20.setCloseIconEnabled(false);
            odi.setCloseIconEnabled(false);
            league.setCloseIconEnabled(false);
            all.setCloseIconEnabled(false);
            women.setCloseIconEnabled(false);
            international.setChipBackgroundColorResource(android.R.color.holo_green_dark);
            t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
            odi.setChipBackgroundColorResource(R.color.scoreRowBackground);
            test.setChipBackgroundColorResource(R.color.scoreRowBackground);
            league.setChipBackgroundColorResource(R.color.scoreRowBackground);
            all.setChipBackgroundColorResource(R.color.scoreRowBackground);
            women.setChipBackgroundColorResource(R.color.scoreRowBackground);
            international.setChecked(true);
            test.setChecked(false);
            t20.setChecked(false);
            odi.setChecked(false);
            league.setChecked(false);
            all.setChecked(false);
            women.setChecked(false);
        }
        if (v == league) {
            if (league.isChecked()) {
                filter("league");
                league.setCloseIconEnabled(true);
                test.setCloseIconEnabled(false);
                t20.setCloseIconEnabled(false);
                odi.setCloseIconEnabled(false);
                all.setCloseIconEnabled(false);
                international.setCloseIconEnabled(false);
                women.setCloseIconEnabled(false);
                league.setChipBackgroundColorResource(android.R.color.holo_green_dark);
                t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);
                test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChecked(true);
                test.setChecked(false);
                t20.setChecked(false);
                odi.setChecked(false);
                all.setChecked(false);
                international.setChecked(false);
                women.setChecked(false);
            }
        }
        if (v == women) {
            if (women.isChecked()) {
                filter("women");
                women.setCloseIconEnabled(true);
                test.setCloseIconEnabled(false);
                t20.setCloseIconEnabled(false);
                odi.setCloseIconEnabled(false);
                league.setCloseIconEnabled(false);
                international.setCloseIconEnabled(false);
                all.setCloseIconEnabled(false);
                women.setChipBackgroundColorResource(android.R.color.holo_green_dark);
                t20.setChipBackgroundColorResource(R.color.scoreRowBackground);
                odi.setChipBackgroundColorResource(R.color.scoreRowBackground);
                test.setChipBackgroundColorResource(R.color.scoreRowBackground);
                league.setChipBackgroundColorResource(R.color.scoreRowBackground);
                international.setChipBackgroundColorResource(R.color.scoreRowBackground);
                all.setChipBackgroundColorResource(R.color.scoreRowBackground);
                women.setChecked(true);
                test.setChecked(false);
                t20.setChecked(false);
                odi.setChecked(false);
                league.setChecked(false);
                international.setChecked(false);
                all.setChecked(false);
            }
        }
    }

}