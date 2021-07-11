package com.cricketexchange.project.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.NewsNormalAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.squareup.picasso.Picasso;

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
    ArrayList<Object> newslist = new ArrayList<>();
    NewsNormalAdapter adapter;
    TextView t_title;
    ImageView i_poster;
    ProgressBar progressBar, progressBar2;
    String id = null;
    boolean isupdated = false;
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
            Log.e("Testhtml", testhtml);
            progressBar.setVisibility(View.GONE);
            load();


            MobileAds.initialize(this, initializationStatus -> {
            });

            AdView mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
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
                Log.d("TAG", " Interstitial not loaded");
            }
            prepareAd();
        }), rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, TimeUnit.SECONDS);
    }

    public void prepareAd() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admov_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    public void update() {
        adapter.MixData();
        progressBar2.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }


    public void load() {
        progressBar2.setVisibility(View.VISIBLE);
        String url = HOST + "news";
        new LoadData().execute(url);
        if (isupdated) {
            update();
        }


    }

    private class LoadData extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... urls) {
            String url = null;
            long a = 34534534;
            int count = urls.length;
            for (int j = 0; j < count; j++) {
                url = urls[j];
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {


                        try {
                            Log.e("RESPONSE", "TRUE");
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < 10; i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("_id");
                                String Maintitle = object.getString("tit");
                                String Secondarytitle = object.getString("des");
                                String img = object.getString("img");
                                String con = object.getString("con");
                                Log.e("RESPONSE :id :", id);
                                NewsModel newsModel = new NewsModel(id, Maintitle, Secondarytitle, "Few Hour Ago", img, con);

                                if (!extras.equals(id)) {
                                    newslist.add(newsModel);
                                }
                            }
                            adapter.setData(newslist);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (isCancelled()) break;
            }
            return a;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();
        }
    }


}