package com.cricketexchange.project.ui.schedule.teams;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.TeamRecycleAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class teams extends Fragment {
    private String HOST = "";
    RecyclerView recyclerView;
    SearchView searchView;
    TeamRecycleAdapter adapter;
    List<String> series = new ArrayList<>();
    List<SquadModel> list = new ArrayList<>();//list
    List<SquadModel> filterd = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        recyclerView = view.findViewById(R.id.team);
        progressBar = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.search);
        searchView.setIconified(false);
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                filterd.clear();
                if (query.equals("")) {
                    filterd.addAll(list);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getSquadFullname().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                            filterd.add(list.get(i));
                        }
                    }

                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                filterd.clear();
                if (newText.equals("")) {
                    filterd.addAll(list);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getSquadFullname().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))) {
                            filterd.add(list.get(i));
                        }
                    }

                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
        series.clear();
        list.clear();
        load();
        return view;
    }


    private void load() {
        loadAllSeries();
        loadAllTeams();
    }

    private void loadAllSeries() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Series");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (int i = 0; i < snapshot.child("1").child("jsondata").child("seriesIdList").getChildrenCount(); i++) {
                    series.add(snapshot.child("1").child("jsondata").child("seriesIdList").child(String.valueOf(i)).getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadAllTeams() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Teams");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (String sid : series) {
                    for (int i = 0; i < snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").getChildrenCount(); i++) {
                        String id = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("id").getValue().toString();
                        String shorName = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("shortName").getValue().toString();
                        String name = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("name").getValue().toString();
                        String logoUrl = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("logoUrl").getValue().toString();
                        String color = "#FFFFFF";
                        SquadModel model = new SquadModel(
                                id,
                                shorName,
                                name,
                                logoUrl,
                                color);
                        if (!list.contains(model)) {
                            list.add(model);
                        }
                    }
                }
                update();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void update() {
        filterd.addAll(list);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeamRecycleAdapter(getContext(), filterd);
        adapter.setHOST(HOST);
        recyclerView.setAdapter(adapter);
        searchView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

}
