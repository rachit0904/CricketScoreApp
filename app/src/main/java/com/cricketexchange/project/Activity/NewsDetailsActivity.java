package com.cricketexchange.project.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.NewsNormalAdapter;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsDetailsActivity extends AppCompatActivity {

    private ImageView close;
    RecyclerView mRecyclerView;
    ArrayList<NewsModel> newslist = new ArrayList<>();
    NewsNormalAdapter adapter;
    TextView t_title;
    ImageView i_poster;
    ProgressBar progressBar;
    String id = null;
    String posturl = null;

    boolean isupdated = false;

    String title, imageposter, description;
    WebView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        close = findViewById(R.id.close);
        t_title = findViewById(R.id.title);
        i_poster = findViewById(R.id.poster);

        close.setOnClickListener(view -> finish());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressBar);
        mDesc = findViewById(R.id.description);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        // Specify an adapter.
        adapter = new NewsNormalAdapter(this, newslist);
        mRecyclerView.setAdapter(adapter);
        //posturl = "https://subject-orifice.000webhostapp.com/api/newsapi.php?id=" + id;


        String extras = getIntent().getStringExtra("id");
        if (extras.equals(null)) {
            Toast.makeText(this, "Something Wents Wrong", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            id = extras;
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            imageposter = getIntent().getStringExtra("imageposter");
            description = getIntent().getStringExtra("html");
            t_title.setText(title);
            Picasso.get().load(imageposter).into(i_poster);
            WebSettings settings = mDesc.getSettings();
            settings.setDefaultTextEncodingName("utf-8");
            mDesc.setBackgroundColor(000000);
            mDesc.loadData("<html><head><style>body{background:#FF000000;color:white}</style></head><body>" + description + "</body></html>", "text/html; charset=utf-8", null);
            progressBar.setVisibility(View.GONE);
            load();
        }


    }


    public void update() {
        adapter.notifyDataSetChanged();


    }


    public void load() {
        String url = "https://temp.booksmotion.com/newsapi.json";
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
                            for (int i = 0; i < 6; i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String Maintitle = object.getString("tit");
                                String Secondarytitle = object.getString("des");
                                String img = object.getString("img");
                                String con = object.getString("con");
                                Log.e("RESPONSE :id :", id);
                                NewsModel newsModel = new NewsModel(id, Maintitle, Secondarytitle, "Few Hour Ago", img, con);
                                newslist.add(newsModel);
                            }

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