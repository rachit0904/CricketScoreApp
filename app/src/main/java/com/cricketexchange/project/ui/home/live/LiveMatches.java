package com.cricketexchange.project.ui.home.live;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesChildAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.schedule.schdeule;
import com.google.android.material.tabs.TabLayout;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LiveMatches extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    CardView card;
    ArrayList<MatchesChildModel> childList = new ArrayList<>();
    ProgressBar progressBar;
    RelativeLayout noMatchLayout;
    public LiveMatches() {
        super(R.layout.fragment_live_matches);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        card = view.findViewById(R.id.upcoming);
        card.setOnClickListener(this);
        noMatchLayout=view.findViewById(R.id.noMatchLayout);
        recyclerView = view.findViewById(R.id.liveMatches);
        progressBar = view.findViewById(R.id.progressBar);
        childList.clear();
        load();
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute(Constants.HOST + "allMatches");
    }





    public void addFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == card) {
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            tabLayout.selectTab(tabLayout.getTabAt(3));
            addFragment(new schdeule());
        }
    }

    private void update() {
        progressBar.setVisibility(View.GONE);
        if(childList.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            noMatchLayout.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            noMatchLayout.setVisibility(View.VISIBLE);
        }
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MatchesChildAdapter adapter = new MatchesChildAdapter(getContext(), childList);
        recyclerView.setAdapter(adapter);
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
                    JSONArray data = object.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);


                        try {

                            MatchesChildModel matchesChildModel = new MatchesChildModel();
                            //fetch all data into childModelList but for date
                            matchesChildModel.setType(obj.getString("cmsMatchType"));
                            matchesChildModel.setIsmultiday(obj.getString("isMultiDay"));
                            matchesChildModel.setIswomen(obj.getString("isWomensMatch"));
                            matchesChildModel.setsId(obj.getJSONObject("series").getString("id"));
                            matchesChildModel.setmId(obj.getString("id"));
                            matchesChildModel.setName(obj.getString("name"));
                            matchesChildModel.setPremiure(obj.getJSONObject("series").getString("name"));//series name
                            matchesChildModel.setStatus(obj.getString("status"));//currentMatchState
                            matchesChildModel.setIsDraw(obj.getString("isMatchDrawn"));//status upcomming mandatory//currentMatchState
                            matchesChildModel.setTeam1(obj.getJSONObject("homeTeam").getString("shortName"));
                            matchesChildModel.setTeam2(obj.getJSONObject("awayTeam").getString("shortName"));

                            String winnigteamid = obj.getString("winningTeamId");
                            if (winnigteamid != null) {
                                String team1id = (obj.getJSONObject("homeTeam").getString("shortName"));
                                // String team2id = (obj.getJSONObject("awayTeam").getString("shortName"));
                                if (winnigteamid.equals(team1id)) {

                                    matchesChildModel.setWinTeamName(obj.getJSONObject("homeTeam").getString("shortName"));
                                } else {

                                    matchesChildModel.setWinTeamName(obj.getJSONObject("awayTeam").getString("shortName"));
                                }


                            }
                            try {
                                String logourl1 = obj.getJSONObject("homeTeam").getString("logoUrl");
                                String logourl2 = obj.getJSONObject("awayTeam").getString("logoUrl");
                                matchesChildModel.setTeam1Url(logourl1);
                                matchesChildModel.setTeam2Url(logourl2);

                            } catch (JSONException je) {
                                matchesChildModel.setTeam1Url("");
                                matchesChildModel.setTeam2Url("");
                            }

                            matchesChildModel.setT1iIsBatting(obj.getJSONObject("homeTeam").getString("isBatting"));
                            matchesChildModel.setT2IsBatting(obj.getJSONObject("awayTeam").getString("isBatting"));
                            matchesChildModel.setMatchSummery(obj.getString("matchSummaryText"));
                            if(!obj.getJSONObject("scores").toString().isEmpty()) {
                                JSONObject scores = obj.getJSONObject("scores");

                                matchesChildModel.setTeam1score(scores.getString("homeScore").split("&")[0].trim());
                                matchesChildModel.setTeam1over(scores.getString("homeOvers").split("&")[0].trim());

                                matchesChildModel.setTeam2score(scores.getString("awayScore").split("&")[0].trim());
                                matchesChildModel.setTeam2over(scores.getString("awayOvers").split("&")[0].trim());
                            }
                            //matchesChildModel.setMatchSummery("Delhi capitals win by 7 wickets");
                            //set date to match modellist and match childmodallist;
                            //set date to match modellist and match childmodallist;
                            String dateTime = obj.getString("startDateTime");
                            String[] arr = dateTime.split("T");//arr[0] gives start date
                            String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
                            //add data to parent and child list
                            String date[] = arr[0].split("-");
                            String sD = (date[2] + "-" + date[1] + "-" + date[0]);
                            SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
                            Date d = new Date();
                            matchesChildModel.setStartDate(sD);
                            matchesChildModel.setStartTime(arr2[0].split(":")[0] + ":" + arr2[0].split(":")[1]);
                            //||matchesChildModel.getStatus().equalsIgnoreCase("LIVE") || matchesChildModel.getStartDate().equals(sobj.format(d))
                            //if(obj.getString("status").equals("INPROGRESS") )
                            if(matchesChildModel.getStatus().equals("COMPLETED")){
                                childList.add(matchesChildModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.e("ASYNCTASK Child", String.valueOf(childList.size()));
            //   Log.e("ASYNCTASK Dates", String.valueOf(dates.size()));

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();
        }
    }




}