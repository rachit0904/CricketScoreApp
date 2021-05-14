package com.cricketexchange.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.NewsNormalAdapter;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;

public class NewsDetailsActivity extends AppCompatActivity {

    private ImageView close, share;
    RecyclerView mRecyclerView;
    ArrayList<NewsModel> newslist = new ArrayList<>();
    NewsNormalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        share = findViewById(R.id.share);
        close = findViewById(R.id.close);
        share.setOnClickListener(view -> share());
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
        adapter = new NewsNormalAdapter(this, newslist);
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
            newslist.add(newsModel);


        }
        adapter.notifyDataSetChanged();
    }


    private void share() {
        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody = "Here is the share content body";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sharing the news");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        startActivity(Intent.createChooser(intent, "Sharing the news"));
    }


}