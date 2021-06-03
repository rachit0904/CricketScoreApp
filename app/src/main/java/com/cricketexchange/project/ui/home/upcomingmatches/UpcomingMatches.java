package com.cricketexchange.project.ui.home.upcomingmatches;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.home.live.LiveMatches;

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

public class UpcomingMatches extends Fragment {
    RecyclerView recyclerView;
    List<MatchesModel> modelList = new ArrayList<>();
    List<MatchesChildModel> childModelList = new ArrayList<>();
    List<MatchesChildModel> childList = new ArrayList<>();
    Set<Date> dates = new TreeSet<>();
    SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
    MatchesAdapter adapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        recyclerView = view.findViewById(R.id.upcomingMatches);

        progressBar = view.findViewById(R.id.progressBar);
        childList.clear();
        childModelList.clear();
        setParentData();
        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute(Constants.HOST + "allMatches");
    }


    private void setChildDate() {
        childModelList = childList;
    }

    private void setParentData() {
        final  long ONE_DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;
        String dateInString = sobj.format(new Date());
        long nextDayMilliSeconds ;
        Date date=new Date();
        for(int i=0;i<7;i++) {
            MatchesModel model=new MatchesModel();
            // Getting the next day and formatting into 'YYYY-MM-DD'
            nextDayMilliSeconds= date.getTime() + ONE_DAY_MILLI_SECONDS;
            Date nextDate= new Date(nextDayMilliSeconds);
            String nextDateStr = sobj.format(nextDate);
            model.setDate(nextDateStr);
            modelList.add(model);
            dateInString=nextDateStr;
            try {
                date = sobj.parse(dateInString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }



    private void update() {
        progressBar.setVisibility(View.GONE);
        recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MatchesAdapter(getContext(), modelList, childList);
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