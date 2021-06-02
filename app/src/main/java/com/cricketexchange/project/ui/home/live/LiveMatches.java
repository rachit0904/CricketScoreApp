package com.cricketexchange.project.ui.home.live;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LiveMatches extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    CardView card;
    ArrayList<MatchesChildModel> childList = new ArrayList<>();
    ProgressBar progressBar;

    public LiveMatches() {
        super(R.layout.fragment_live_matches);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        card = view.findViewById(R.id.upcoming);
        card.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.liveMatches);
        progressBar = view.findViewById(R.id.progressBar);
        childList.clear();
        load();
    }

    private void load() {

        progressBar.setVisibility(View.VISIBLE);
        new Load().execute(Constants.HOST +"getTodayMatches");
    }


    @NotNull
    private List<MatchesChildModel> getChildData() {
        List<MatchesChildModel> childModelList = new ArrayList<>();
        MatchesChildModel matchesChildModel = new MatchesChildModel("CSK", "MI", "Indian Premiure League", "MI", "MI won by 4wkts", "INPROGRESS", "1", "", "", "176-5", "150-7", "19.5", "20.0");
        childModelList.add(matchesChildModel);
        MatchesChildModel matchesChildModel2 = new MatchesChildModel("DC", "MI", "Indian Premiure League", "DC", "DC won by 30 runs", "INPROGRESS", "12", "", "", "126-2", "180-9", "7.5", "20.0");
        childModelList.add(matchesChildModel2);
        MatchesChildModel matchesChildModel3 = new MatchesChildModel("DC", "MI", "Indian Premiure League", "DC", "DC won by 30 runs", "INPROGRESS", "12", "", "", "126-2", "180-9", "7.5", "20.0");
        childModelList.add(matchesChildModel3);
        return childModelList;
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

                            matchesChildModel.setsId(obj.getString("jsondata").split("S")[0]);
                            matchesChildModel.setmId(obj.getString("jsondata").split("S")[1]);
                            matchesChildModel.setPremiure(obj.getJSONObject("jsondata").getJSONObject("meta").getJSONObject("series").getString("name"));//series name
                            matchesChildModel.setStatus(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("status"));//status upcomming mandatory//currentMatchState
                            //matchesChildModel.setIsDraw(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("isMatchDrawn"));//status upcomming mandatory//currentMatchState
                            matchesChildModel.setTeam1(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("homeTeam").getString("shortName"));
                            matchesChildModel.setTeam2(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("awayTeam").getString("shortName"));


                            try {
                                String logourl1 = obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("homeTeam").getString("logoUrl");
                                String logourl2 = obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("awayTeam").getString("logoUrl");
                                matchesChildModel.setTeam1Url(logourl1);
                                matchesChildModel.setTeam2Url(logourl2);

                            } catch (JSONException je) {
                                matchesChildModel.setTeam1Url("");
                                matchesChildModel.setTeam2Url("");
                            }

                            matchesChildModel.setT1iIsBatting(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("homeTeam").getString("isBatting"));
                            matchesChildModel.setT2IsBatting(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("awayTeam").getString("isBatting"));
                            matchesChildModel.setMatchSummery(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("matchSummaryText"));

                            JSONObject scores = obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("scores");

                            matchesChildModel.setTeam1score(scores.getString("homeScore").split("&")[0].trim());
                            matchesChildModel.setTeam1over(scores.getString("homeOvers").split("&")[0].trim());

                            matchesChildModel.setTeam2score(scores.getString("awayScore").split("&")[0].trim());
                            matchesChildModel.setTeam2over(scores.getString("awayOvers").split("&")[0].trim());

                            //matchesChildModel.setMatchSummery("Delhi capitals win by 7 wickets");
                            //set date to match modellist and match childmodallist;
                            //set date to match modellist and match childmodallist;
                            //  String dateTime = obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("startDateTime");
                            // String[] arr = dateTime.split("T");//arr[0] gives start date
                            // String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
                            //add data to parent and child list
                            //     String date[] = arr[0].split("-");
                            //     String sD = (date[2] + "-" + date[1] + "-" + date[0]);
                            //       SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
                            //      Date d = null;


                            // matchesChildModel.setStartDate(sD);
                            // matchesChildModel.setStartTime(arr2[0].split(":")[0] + ":" + arr2[0].split(":")[1]);
                            if (matchesChildModel.getStatus().equalsIgnoreCase("INPROGRESS") || matchesChildModel.getStatus().equalsIgnoreCase("LIVE")) {
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


            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();
        }
    }


}