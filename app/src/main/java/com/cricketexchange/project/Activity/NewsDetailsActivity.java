package com.cricketexchange.project.Activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.NewsNormalAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsDetailsActivity extends AppCompatActivity {

    private String HOST = "";
    private ImageView close;
    public static final int NUMBER_OF_ADS = 5;
    public static final int ADS_PER_POST = 4;
    private AdLoader adLoader;
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    RecyclerView mRecyclerView;
    final ArrayList<Object> newslist = new ArrayList<>();
    NewsNormalAdapter adapter;
    TextView t_title;
    ImageView i_poster;
    ProgressBar progressBar, progressBar2;
    String id = null;
    final boolean isupdated = false;
    String title, imageposter, description;
    WebView des;
    String extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        HOST = getIntent().getStringExtra("HOST");
        close = findViewById(R.id.close);
        t_title = findViewById(R.id.title);
        i_poster = findViewById(R.id.poster);

        close.setOnClickListener(view -> finish());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        des = findViewById(R.id.webview);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Specify an adapter.
        adapter = new NewsNormalAdapter(this);
        adapter.setHOST(HOST);
        mRecyclerView.setAdapter(adapter);
        MobileAds.initialize(this, initializationStatus -> {
        });

        extras = getIntent().getStringExtra("id");
        if (extras == null) {
            Toast.makeText(this, "Something Wents Wrong", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            imageposter = getIntent().getStringExtra("imageposter");
            description = getIntent().getStringExtra("html");
            t_title.setText(title);
            Picasso.get().load(imageposter).into(i_poster);
            String testhtml = "<html><head><style>body{background:#FF000000;color:white}</style></head><body>" + description + "</body></html>";
            des.setBackgroundColor(000);
            des.loadDataWithBaseURL(null, testhtml, "text/html", null, null);
            progressBar.setVisibility(View.GONE);
            load();


            MobileAds.initialize(this, initializationStatus -> {
            });

            SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
            String s1 = sharedPreferences.getString("ban1", "ca-app-pub-3940256099942544/6300978111");

           RelativeLayout mAdView = findViewById(R.id.adView);
            mAdView.setGravity(RelativeLayout.CENTER_HORIZONTAL);
            AdView mAdview = new AdView(this);

            mAdview.setAdSize(AdSize.BANNER);
            mAdview.setAdUnitId(s1);
            mAdView.addView(mAdview);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdview.loadAd(adRequest);
            loadinterstitialads();
        }


    }

    InterstitialAd mInterstitialAd;

    private void loadinterstitialads() {
        Random rand = new Random();
        prepareAd();
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> runOnUiThread(() -> {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();

            } else {
                prepareAd();
            }

        }), rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, TimeUnit.SECONDS);
    }

    public void prepareAd() {

        SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
        String i1 = sharedPreferences.getString("in2", "ca-app-pub-3940256099942544%2F1033173712");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(i1);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    public void update() {
        adapter.setData(newslist);
        adapter.MixData();
        progressBar2.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }


    public void load() {
        progressBar2.setVisibility(View.VISIBLE);
        loadNewsDetail();
        if (isupdated) {
            update();
        }


    }

    private void loadNewsDetail() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("News");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String id = ds.child("_id").getValue().toString();
                    String Maintitle = ds.child("tit").getValue().toString();
                    String Secondarytitle = ds.child("des").getValue().toString();
                    String img = ds.child("img").getValue().toString();
                    String con = ds.child("con").getValue().toString();
                    String author = ds.child("aut").getValue().toString();
                    NewsModel newsModel = new NewsModel(id, Maintitle, Secondarytitle, author, img, con);
                    if (!id.equalsIgnoreCase(extras)) {
                        newslist.add(newsModel);
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