package com.cricketexchange.project.ui.series;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.SeriesNameAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.schedule.schdeule;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class seriesFrag extends Fragment implements View.OnClickListener {
    RecyclerView seriesRv;
    Button seeAllBtn;
    SeriesNameAdapter adapter;
    ArrayList<SeriesModel> datalist = new ArrayList<>();
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    String HOST = "";
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.series_fragment, container, false);
        seriesRv = root.findViewById(R.id.seriesRv);
        seeAllBtn = root.findViewById(R.id.allSeries);
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        seeAllBtn.setOnClickListener(this);
        progressBar = root.findViewById(R.id.progressBar);
        datalist.clear();
        load();
        return root;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.allSeries) {
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            tabLayout.selectTab(tabLayout.getTabAt(3));
            addFragment(new schdeule());
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();

    }

    private void update() {

        seriesRv.hasFixedSize();
        seriesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SeriesNameAdapter(getActivity(), datalist);
        seriesRv.setAdapter(adapter);
        adapter.setHOST(HOST);
        progressBar.setVisibility(View.GONE);

    }


    private void load() {
        loadLiveSeries();
    }

    private void loadLiveSeries() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Series");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int j = (int) snapshot.child("1").child("jsondata").child("seriesList").child("series").getChildrenCount() - 1;
                for (int i = 0; i <= j; i++) {
                    String id = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("id").getValue().toString();
                    String name = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("name").getValue().toString();
                    String status = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("status").getValue().toString();
                    String startDateTime = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("startDateTime").getValue().toString();
                    String endDateTime = snapshot.child("1").child("jsondata").child("seriesList").child("series").child(String.valueOf(i)).child("endDateTime").getValue().toString();
                    SeriesModel model = new SeriesModel();
                    model.setSeriesName(name);
                    model.setSid(id);
                    model.setStartDate(startDateTime);
                    model.setStatus(status);
                    if(status.equalsIgnoreCase("LIVE") || status.equalsIgnoreCase("INPROGRESS")) {
                        datalist.add(model);
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