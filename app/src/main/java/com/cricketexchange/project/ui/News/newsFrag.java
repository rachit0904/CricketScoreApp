package com.cricketexchange.project.ui.News;

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

import com.cricketexchange.project.Adapter.Recyclerview.NewsRecyclerAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
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


public class newsFrag extends Fragment {

    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 5;
    public static final int ADS_PER_POST = 4;
    private String HOST = "";


    // The AdLoader used to load ads.
    private AdLoader adLoader;

    // List of MenuItems and native ads that populate the RecyclerView.
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        //init the ads

        HOST = requireActivity().getIntent().getStringExtra("HOST");
        progressBar = view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // Specify an adapter.
        adapter = new NewsRecyclerAdapter(getActivity());
        adapter.setHOST(HOST);
        mRecyclerView.setAdapter(adapter);
        MobileAds.initialize(getActivity(), initializationStatus -> {
        });
        mRecyclerViewItems.clear();
//        Picasso.setSingletonInstance(new Picasso.Builder(getActivity()).build());
        addMenuItemsFromJson();
        return view;
    }


    private void addMenuItemsFromJson() {
        progressBar.setVisibility(View.VISIBLE);
        loadNews();
    }

    private void loadNews() {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("News");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for( DataSnapshot ds : snapshot.getChildren() ){
                    String id =ds.child("_id").getValue().toString();
                    String Maintitle = ds.child("tit").getValue().toString();
                    String Secondarytitle = ds.child("des").getValue().toString();
                    String img = ds.child("img").getValue().toString();
                    String con = ds.child("con").getValue().toString();
                    String author=ds.child("aut").getValue().toString();
                    NewsModel newsModel = new NewsModel(id, Maintitle, Secondarytitle,author , img, con);
                    mRecyclerViewItems.add(newsModel);
                }
                update();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void update() {
        adapter.setData(mRecyclerViewItems);
        adapter.MixData();
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

}
