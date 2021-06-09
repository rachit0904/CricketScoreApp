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


        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute(HOST + "allMatches");
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


    MatchesAdapter adapter;

    private void update(Boolean isAt) {


        tours.setVisibility(View.VISIBLE);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

                    for (int i = data.length() - 1; i > 0; i--) {
                        JSONObject obj = data.getJSONObject(i);
                        Log.i("DAYSFRAGMENT", "FFFOOOORRR\n\n\n\n\n\n");


                        try {

                            MatchesChildModel matchesChildModel = new MatchesChildModel();
                            //fetch all data into childModelList but for date

                            try {
                                matchesChildModel.setType(obj.getString("cmsMatchType"));
                            } catch (JSONException a) {
                                matchesChildModel.setType(("null"));
                            }
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
                            String winnigteamid;

                            try {
                                winnigteamid = obj.getString("winningTeamId");
                            } catch (JSONException e) {
                                winnigteamid = "";
                            }
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
                            try {
                                JSONObject scores = obj.getJSONObject("scores");

                                matchesChildModel.setTeam1score(scores.getString("homeScore").split("&")[0].trim());
                                matchesChildModel.setTeam1over(scores.getString("homeOvers").split("&")[0].trim());

                                matchesChildModel.setTeam2score(scores.getString("awayScore").split("&")[0].trim());
                                matchesChildModel.setTeam2over(scores.getString("awayOvers").split("&")[0].trim());

                            } catch (JSONException a) {

                                matchesChildModel.setTeam1score("0");
                                matchesChildModel.setTeam1over("0");

                                matchesChildModel.setTeam2score("0");
                                matchesChildModel.setTeam2over("0");

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
                            Date d = null;
                            try {
                                d = sobj.parse(sD);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dates.add(d);

                            matchesChildModel.setStartDate(sD);
                            matchesChildModel.setStartTime(arr2[0].split(":")[0] + ":" + arr2[0].split(":")[1]);

                            childModelList.add(matchesChildModel);


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