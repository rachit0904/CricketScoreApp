package com.cricketexchange.project.ui.home.upcomingmatches;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    List<MatchesModel> parentList = new ArrayList<>();
    List<MatchesChildModel> childList = new ArrayList<>();
    Set<String> dates = new TreeSet<>();
    MatchesAdapter adapter;
    String months[] = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        recyclerView = view.findViewById(R.id.upcomingMatches);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            load();
        }
    }

    private void setChildDate() {
        childModelList = childList;
    }

    private void setParentData() {

        for (String x : dates) {
            MatchesModel model = new MatchesModel();
            model.setDate(x);
            modelList.add(model);
        }
    }

    private void update() {
        setParentData();
        setChildDate();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // modelList.clear();
        //childModelList.clear();
        adapter = new MatchesAdapter(getContext(), modelList, childModelList);
        recyclerView.setAdapter(adapter);
        Log.e("Update", "updated");
    }

    private void setData() {
        for (int i = 0; i < 2; i++) {
            MatchesChildModel matchesChildModel = new MatchesChildModel();
            //fetch all data into childModelList but for date
            matchesChildModel.setsId("");
            matchesChildModel.setmId("");
            matchesChildModel.setPremiure("IPL 2021");//series name
            matchesChildModel.setStatus("UPCOMING");//status upcomming mandatory
            matchesChildModel.setTeam1("RCB");
            matchesChildModel.setTeam2("DC");
            matchesChildModel.setTeam1Url("");
            matchesChildModel.setTeam2Url("");
            matchesChildModel.setT1iIsBatting("true");
            matchesChildModel.setT2IsBatting("false");
            matchesChildModel.setMatchSummery("Delhi capitals win by 7 wickets");
            //set date to match modellist and match childmodallist;
            String dateTime = "2021-06-10T10:00:00Z";
            String[] arr = dateTime.split("T");//arr[0] gives start date
            String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
            //add data to parent and child list
            String date[] = arr[0].split("-");
            String sD = (date[2] + " " + months[Integer.parseInt(date[1]) - 1] + "," + date[0]);
            dates.add(sD);

            matchesChildModel.setStartDate(sD);
            matchesChildModel.setStartTime(arr2[0]);
            childList.add(matchesChildModel);

        }
    }

    private void load() {
        new Load().execute("http://3.108.39.214/getNextWeekMatches");
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
                            matchesChildModel.setMatchSummery("");
                            //set date to match modellist and match childmodallist;
                            String dateTime = obj.getJSONObject("jsondata").getJSONObject("matchDetail").getJSONObject("matchSummary").getString("startDateTime");
                            String[] arr = dateTime.split("T");//arr[0] gives start date
                            String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
                            //add data to parent and child list
                            String date[] = arr[0].split("-");
                            String sD = (date[2] + " " + months[Integer.parseInt(date[1]) - 1] + "," + date[0]);
                            dates.add(sD);
                            matchesChildModel.setStartDate(sD);
                            matchesChildModel.setStartTime(arr2[0].split(":")[0] + ":" + arr2[0].split(":")[1]);
                            childList.add(matchesChildModel);
                          //  Log.e("Dates Forloop", sD);
                         //   Log.e("Premiere Forloop",matchesChildModel.getPremiure() );
                          //  Log.e("MID Forloop", sD);
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
            Log.e("ASYNCTASK Child", String.valueOf(childList.size()));
            Log.e("ASYNCTASK Dates", String.valueOf(dates.size()));

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();
        }
    }


}