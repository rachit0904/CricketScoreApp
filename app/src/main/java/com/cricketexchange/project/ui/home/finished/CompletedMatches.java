package com.cricketexchange.project.ui.home.finished;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.home.upcomingmatches.UpcomingMatches;

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

public class CompletedMatches extends Fragment {
    RecyclerView recyclerView;
    List<MatchesModel> modelList = new ArrayList<>();
    List<MatchesChildModel> childModelList = new ArrayList<>();
    List<MatchesChildModel> childList = new ArrayList<>();
    Set<Date> dates = new TreeSet<>();
    SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_matches, container, false);
        recyclerView = view.findViewById(R.id.finishedMatches);
        if (childList.size() > 0) {
            update(false);
        } else {
            load();
        }
        return view;
    }


    private void load() {
        new Load().execute("http://3.108.39.214/getPreviousWeekMatches");
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


    private void update(Boolean isAt) {
        if (isAt) {
            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            setParentData();
            setChildDate();
            MatchesAdapter adapter = new MatchesAdapter(getContext(), modelList, childModelList);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            MatchesAdapter adapter = new MatchesAdapter(getContext(), modelList, childModelList);
            recyclerView.setAdapter(adapter);
        }

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
                            matchesChildModel.setStatus(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("currentMatchState"));//status upcomming mandatory//currentMatchState
                            matchesChildModel.setIsDraw(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("isMatchDrawn"));//status upcomming mandatory//currentMatchState
                            matchesChildModel.setTeam1(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("homeTeam").getString("shortName"));
                            matchesChildModel.setTeam2(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("awayTeam").getString("shortName"));

                            String winnigteamid = obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("winningTeamId");
                            if (winnigteamid != null) {
                                String team1id = (obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("homeTeam").getString("id"));
                                String team2id = (obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("awayTeam").getString("id"));
                                if (winnigteamid.equals(team1id)) {

                                    matchesChildModel.setWinTeamName(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("homeTeam").getString("shortName"));
                                } else {

                                    matchesChildModel.setWinTeamName(obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getJSONObject("awayTeam").getString("shortName"));
                                }


                            }
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
                            String dateTime = obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("startDateTime");
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
                            if (matchesChildModel.getStatus().equalsIgnoreCase("COMPLETED")) {
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
            update(true);
        }
    }


}

