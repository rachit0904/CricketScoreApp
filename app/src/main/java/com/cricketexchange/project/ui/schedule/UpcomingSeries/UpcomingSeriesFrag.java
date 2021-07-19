package com.cricketexchange.project.ui.schedule.UpcomingSeries;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.UpcomingSeriesAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.UpcomingSeriesModel;
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

public class UpcomingSeriesFrag extends Fragment implements View.OnClickListener {
    private String HOST = "";
    RecyclerView recyclerView;
    final List<UpcomingSeriesModel> list = new ArrayList<>();
    final List<SeriesModel> childList = new ArrayList<>();
    final Set<Date> dates = new TreeSet<>();
    SharedPreferences preferences;
    final List<SeriesModel> cList = new ArrayList<>();
    HorizontalScrollView scrollView;
    ChipGroup tours;
    Chip all, test, t20, odi, international, league, women;
    final List<SeriesModel> filterdcList = new ArrayList<>();
    final SimpleDateFormat sobj = new SimpleDateFormat("MM-yyyy");
    UpcomingSeriesAdapter adapter;
    ProgressBar progressBar;
    String status;
    DatabaseReference databaseReference;
    final String[] arr = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_series, container, false);
        recyclerView = view.findViewById(R.id.upcomingSeriesRv);
        preferences = requireActivity().getSharedPreferences(Constants.Filter, 0);
        progressBar = view.findViewById(R.id.progressBar);
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        list.clear();
        filterdcList.clear();
        childList.clear();
        tours = view.findViewById(R.id.tours);
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


    private void setParentData() {

        for (Date x : dates) {
            UpcomingSeriesModel model = new UpcomingSeriesModel();
            model.setDate(sobj.format(x));
            list.add(model);
        }
    }

    private void filter(String type) {
        progressBar.setVisibility(View.VISIBLE);
        filterdcList.clear();
        switch (type) {
            case "odi":
                for (int i = 0; i < cList.size(); i++) {
                    if (cList.get(i).getType().toLowerCase(Locale.ROOT).contains("odi")) {
                        filterdcList.add(cList.get(i));
                    } else if (cList.get(i).getSeriesName().toLowerCase(Locale.ROOT).contains("odi")) {
                        filterdcList.add(cList.get(i));
                    }
                }
                break;
            case "t20":
                for (int i = 0; i < cList.size(); i++) {
                    if (cList.get(i).getType().toLowerCase(Locale.ROOT).contains("t20")) {
                        filterdcList.add(cList.get(i));
                    } else if (cList.get(i).getSeriesName().toLowerCase(Locale.ROOT).contains("t20")) {
                        filterdcList.add(cList.get(i));
                    }
                }
                break;
            case "test":
                for (int i = 0; i < cList.size(); i++) {
                    if (cList.get(i).getType().toLowerCase(Locale.ROOT).contains("test")) {
                        filterdcList.add(cList.get(i));
                    } else if (cList.get(i).getSeriesName().toLowerCase(Locale.ROOT).contains("test")) {
                        filterdcList.add(cList.get(i));
                    }
                }
                break;
            case "international":
                for (int i = 0; i < cList.size(); i++) {
                    if (cList.get(i).getType().toLowerCase(Locale.ROOT).contains("intl")) {
                        filterdcList.add(cList.get(i));
                    } else if (cList.get(i).getSeriesName().toLowerCase(Locale.ROOT).contains("world")) {
                        filterdcList.add(cList.get(i));
                    }
                }
                break;
            case "league":
                for (int i = 0; i < cList.size(); i++) {
                    if (cList.get(i).getSeriesName().toLowerCase(Locale.ROOT).contains("test")) {
                        filterdcList.add(cList.get(i));
                    }
                }
                break;
            case "women":
                for (int i = 0; i < cList.size(); i++) {
                    if (cList.get(i).getType().toLowerCase(Locale.ROOT).contains("women")) {
                        filterdcList.add(cList.get(i));
                    } else if (cList.get(i).getSeriesName().toLowerCase(Locale.ROOT).contains("women")) {
                        filterdcList.add(cList.get(i));
                    }
                }
                break;
            default:
                filterdcList.addAll(cList);

        }
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void update() {
        filterdcList.addAll(cList);
        progressBar.setVisibility(View.GONE);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UpcomingSeriesAdapter(getActivity(), list, filterdcList);
        adapter.setHOST(HOST);
        recyclerView.setAdapter(adapter);
        setParentData();
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        loadLiveSeries();
    }

    @Override
    public void onClick(View v) {
        if (v == all) {
            assert all != null;
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
            assert odi != null;
            if (odi.isChecked()) {
                filter("odi");

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

            assert t20 != null;
            if (t20.isChecked()) {
                filter("t20");

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
            assert test != null;
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
            assert international != null;
            if (international.isChecked()) {
                filter("international");
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
        }
        if (v == league) {
            assert league != null;
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

    private void loadLiveSeries() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Series");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int j = (int) snapshot.child("1").child("jsondata").child("seriesList").child("series").getChildrenCount() - 1;
                for (int i = 0; i <= j; i++) {
                    SeriesModel SsriesModel = new SeriesModel();
                    //fetch all data into childModelList but for date
                    String id = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("id").getValue().toString();
                    String name = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("name").getValue().toString();
                    String status = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("status").getValue().toString();
                    String startDateTime = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("startDateTime").getValue().toString();
                    String date[] = startDateTime.split("/");
                    String enddate = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("endDateTime").getValue().toString();
                    String date2[] = enddate.split("/");
                    String sD = (date[1] + " " + arr[Integer.parseInt(date[0]) - 1]), eD = (date2[1] + " " + arr[Integer.parseInt(date2[0]) - 1]);
                    SsriesModel.setDuration(sD + "  to  " + eD);
                    SsriesModel.setSid(id);
                    SsriesModel.setSeriesName(name);
                    Date d = null;
                    try {
                        d = sobj.parse(date[0] + "-" + date[2]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SsriesModel.setStartDate(sobj.format(d));
                    if(status.equalsIgnoreCase("UPCOMING") ) {
                        dates.add(d);
                        cList.add(SsriesModel);
                    }
                }
                update();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}
