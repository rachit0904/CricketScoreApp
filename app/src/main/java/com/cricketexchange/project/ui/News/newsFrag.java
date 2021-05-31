package com.cricketexchange.project.ui.News;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.NewsRecyclerAdapter;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.squareup.picasso.Picasso;

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
        mRecyclerView.setAdapter(adapter);
        MobileAds.initialize(getActivity(), initializationStatus -> {
        });
//        Picasso.setSingletonInstance(new Picasso.Builder(getActivity()).build());
        addMenuItemsFromJson();




        return view;
    }


    private void addMenuItemsFromJson() {
        progressBar.setVisibility(View.VISIBLE);
        new LoadData().execute("http://3.108.39.214/news");
    }


    private void update() {
        adapter.MixData();
        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
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
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("_id");
                                String Maintitle = object.getString("tit");
                                String Secondarytitle = object.getString("des");
                                String img = object.getString("img");
                                String con = object.getString("con");
                                NewsModel newsModel = new NewsModel(id, Maintitle, Secondarytitle, "Few Hour Ago", img, con);
                                mRecyclerViewItems.add(newsModel);

                            }
                            adapter.setData(mRecyclerViewItems);

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
