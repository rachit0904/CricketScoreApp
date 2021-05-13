package com.cricketexchange.project.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.NewsNormalAdapter;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;

public class NewsDetails extends AppCompatActivity {

    private ImageView close;
    RecyclerView mRecyclerView;
    ArrayList<NewsModel> newsList = new ArrayList<>();
    NewsNormalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        close = findViewById(R.id.close);
        close.setOnClickListener(view -> finish());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Specify an adapter.
        adapter = new NewsNormalAdapter(this, newsList);
        mRecyclerView.setAdapter(adapter);

        load();
    }

    private void load() {
        int size = 6;

        for (int i = 0; i < size; i++) {
//            if (i != 0 && i % ADS_PER_POST == 0) {
//                mRecyclerViewItems.add(null);
//            } else {
//
//            }
            NewsModel newsModel = new NewsModel("" + i, this.getString(R.string.sample_title), this.getString(R.string.sample_title), "2 hours ago", "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png");
            newsList.add(newsModel);


        }
        adapter.notifyDataSetChanged();
    }

}