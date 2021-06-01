package com.cricketexchange.project.ui.series.match;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MatchDetailFrag extends Fragment {
    RecyclerView recyclerView;
    List<MatchesModel> modelList = new ArrayList<>();
    List<MatchesChildModel> childModelList = new ArrayList<>();
    List<MatchesModel> parentList = new ArrayList<>();
    List<MatchesChildModel> childList = new ArrayList<>();
    Set<Date> dates = new TreeSet<>();
    SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
    String sid = "";
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_detail, container, false);
        recyclerView = view.findViewById(R.id.matchDetailRv);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.hasFixedSize();
        sid = requireActivity().getIntent().getStringExtra("sid");
        dates.clear();
        childList.clear();
        childModelList.clear();
        modelList.clear();

        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute("http://3.108.39.214/AllMatchesBySID?id=" + sid);
    }

    private void setChildDate() {
        childModelList = childList;
    }

    private void setParentData() {
        for (Date x : dates) {
            MatchesModel model = new MatchesModel();
            model.setDate(sobj.format(x));
            modelList.add(model);
        }
    }

    private void setData() {
        for (int i = 0; i < 3; i++) {
            MatchesChildModel matchesChildModel = new MatchesChildModel();
            //fetch all data into childModelList but for date
            matchesChildModel.setsId("");
            matchesChildModel.setmId("");
            matchesChildModel.setPremiure("IPL 2021");//series name
            matchesChildModel.setStatus("COMPLETED");
            matchesChildModel.setIsDraw("false");
            matchesChildModel.setWinTeamName("DC");
            matchesChildModel.setTeam1("RCB");
            matchesChildModel.setTeam2("DC");
            matchesChildModel.setTeam1Url("");
            matchesChildModel.setTeam2Url("");
            matchesChildModel.setT1iIsBatting("true");
            matchesChildModel.setT2IsBatting("false");
            if (matchesChildModel.getT1iIsBatting().equalsIgnoreCase("true")) {
                matchesChildModel.setTeam1score("136-4");
                matchesChildModel.setTeam1over("13.2");
            } else {
                matchesChildModel.setTeam1score("");
                matchesChildModel.setTeam1over("");
            }
            if (matchesChildModel.getT2IsBatting().equalsIgnoreCase("true")) {
                matchesChildModel.setTeam2score("136-4");
                matchesChildModel.setTeam2over("12.4");
            } else {
                matchesChildModel.setTeam2score("");
                matchesChildModel.setTeam2over("");
            }
            matchesChildModel.setMatchSummery("Delhi capitals win by 7 wickets");
            //set date to match modellist and match childmodallist;
            String dateTime = "2021-06-03T10:00:00Z";
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
            matchesChildModel.setStartTime(arr2[0]);
            childList.add(matchesChildModel);
        }
    }

    private void update(boolean b) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        modelList.clear();
//        childModelList.clear();
        setParentData();
        setChildDate();
        MatchesAdapter adapter = new MatchesAdapter(getContext(), modelList, childModelList);
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
                    JSONArray data = object.getJSONObject("data").getJSONObject("matchList").getJSONArray("matches");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);


                        try {
                            MatchesChildModel matchesChildModel = new MatchesChildModel();
                            //fetch all data into childModelList but for date

                            matchesChildModel.setsId(obj.getJSONObject("series").getString("id"));
                            matchesChildModel.setmId(obj.getString("id"));
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

                            JSONObject scores = obj.getJSONObject("scores");

                            matchesChildModel.setTeam1score(scores.getString("homeScore").split("&")[0].trim());
                            matchesChildModel.setTeam1over(scores.getString("homeOvers").split("&")[0].trim());

                            matchesChildModel.setTeam2score(scores.getString("awayScore").split("&")[0].trim());
                            matchesChildModel.setTeam2over(scores.getString("awayOvers").split("&")[0].trim());

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
                            Date d = null;
                            try {
                                d = sobj.parse(sD);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dates.add(d);
                            matchesChildModel.setStartDate(sD);
                            matchesChildModel.setStartTime(arr2[0].split(":")[0] + ":" + arr2[0].split(":")[1]);

                            childList.add(matchesChildModel);


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
            update(true);
        }
    }


}