package com.cricketexchange.project.ui.series.squad;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.SquadParentAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;
import com.google.android.material.snackbar.Snackbar;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SquadFrag extends Fragment {
    RecyclerView recyclerView;
    SquadParentAdapter adapter;
    List<SquadModel> list = new ArrayList<>();
    String sid = "";
    String HOST;
    ProgressBar progressBar;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_squad, container, false);
        recyclerView = view.findViewById(R.id.squadRv);
        sid = requireActivity().getIntent().getStringExtra("sid");
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        list.clear();
        progressBar = view.findViewById(R.id.progressBar);
        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        loadSeriesTeams(sid);
    }

    private void loadSeriesTeams(String sid) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Teams");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(int i=0;i<snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").getChildrenCount();i++) {
                    String id = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("id").getValue().toString();
                    String shorName = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("shortName").getValue().toString();
                    String name = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("name").getValue().toString();
                    String logoUrl = snapshot.child(sid).child("jsondata").child("seriesTeams").child("teams").child(String.valueOf(i)).child("logoUrl").getValue().toString();
                    String color="#FFFFFF";
                    SquadModel model = new SquadModel(
                            id,
                            shorName,
                            name,
                            logoUrl,
                            color);
                    list.add(model);
                }
                update();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void update() {
        progressBar.setVisibility(View.GONE);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SquadParentAdapter(getContext(), list);
        adapter.setHOST(HOST);
        recyclerView.setAdapter(adapter);
    }

}