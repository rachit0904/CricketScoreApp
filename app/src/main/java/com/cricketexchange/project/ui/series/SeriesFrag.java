package com.cricketexchange.project.ui.series;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.SeriesNameAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.home.upcomingmatches.UpcomingMatches;
import com.cricketexchange.project.ui.schedule.schdeule;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class seriesFrag extends Fragment implements View.OnClickListener {
    RecyclerView seriesRv;
    Button seeAllBtn;
    RecyclerView.Adapter adapter;
    ArrayList<SeriesModel> datalist = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.series_fragment, container, false);
        seriesRv = root.findViewById(R.id.seriesRv);
        seeAllBtn = root.findViewById(R.id.allSeries);
        seeAllBtn.setOnClickListener(this);

        if (datalist.size() > 0) {
        } else {
            load();
        }


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

    }


    private void load() {
        new Load().execute(Constants.HOST + "AllSeriesInProgress");
    }


    private class Load extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... urls) {
            long totalSize = 0;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray data = object.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);


                        try {
                            String id = obj.getString("id");
                            String name = obj.getString("name");
                            String statisticsProvider = obj.getString("statisticsProvider");
                            String shieldImageUrl = obj.getString("shieldImageUrl");
                            String status = obj.getString("status");
                            String startDateTime = obj.getString("startDateTime");
                            String endDateTime = obj.getString("endDateTime");
                            SeriesModel model = new SeriesModel();

                            model.setSeriesName(name);
                            model.setSid(id);
                            model.setStartDate(startDateTime);
                            model.setStatus(status);

                            datalist.add(model);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();
        }
    }

}