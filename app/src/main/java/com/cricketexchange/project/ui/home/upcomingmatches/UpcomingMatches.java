package com.cricketexchange.project.ui.home.upcomingmatches;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.home.live.LiveMatches;
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
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpcomingMatches extends Fragment {
    private String HOST = "";
    RecyclerView recyclerView;
    final List<MatchesModel> modelList = new ArrayList<>();
    List<MatchesChildModel> childModelList = new ArrayList<>();
    final List<MatchesChildModel> childList = new ArrayList<>();
    final Set<Date> dates = new TreeSet<>();
    final SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
    MatchesAdapter adapter;
    ProgressBar progressBar;
    final List<String> series =new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        recyclerView = view.findViewById(R.id.upcomingMatches);

        progressBar = view.findViewById(R.id.progressBar);
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        series.clear();
        modelList.clear();
        childModelList.clear();
        load();
        return view;
    }


    private void setChildDate() {
        childModelList = childList;
    }

    private void setParentData() {
        final long ONE_DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;
        String dateInString = sobj.format(new Date());
        long nextDayMilliSeconds;
        Date date = new Date();
        for (int i = 0; i < 7; i++) {
            MatchesModel model = new MatchesModel();
            // Getting the next day and formatting into 'YYYY-MM-DD'
            nextDayMilliSeconds = date.getTime() + ONE_DAY_MILLI_SECONDS;
            Date nextDate = new Date(nextDayMilliSeconds);
            String nextDateStr = sobj.format(nextDate);
            model.setDate(nextDateStr);
            modelList.add(model);
            dateInString = nextDateStr;
            try {
                date = sobj.parse(dateInString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    private void update() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Objects.requireNonNull(recyclerView.getLayoutManager()).setMeasurementCacheEnabled(false);
//        modelList.clear();
//        childModelList.clear();
        setParentData();
        setChildDate();
        adapter = new MatchesAdapter(getContext(), modelList, childModelList);
        adapter.setHOST(HOST);
        recyclerView.setAdapter(adapter);

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
                        if (matchesChildModel.getStatus().equalsIgnoreCase("UPCOMING")) {
                            childList.add(matchesChildModel);
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

}