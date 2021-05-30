package com.cricketexchange.project.ui.series.pointstable;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketexchange.project.Adapter.Recyclerview.ScoreCardAdapter;
import com.cricketexchange.project.Models.ScoreCardModel;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PointsTableFrag extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ScoreCardModel> scoreCardModelList = new ArrayList<>();
    String sid = String.valueOf(2739);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_points_table, container, false);
        recyclerView = view.findViewById(R.id.scoresRowRv);

        load();
        return view;
    }

    private List<ScoreCardModel> getData() {
        List<ScoreCardModel> modelList = new ArrayList<>();
        ScoreCardModel model1 = new ScoreCardModel("1", "", "DC",
                "3", "2", "1",
                "2", "9", "+0.632");
        modelList.add(model1);
        ScoreCardModel mode2 = new ScoreCardModel("2", "", "MI",
                "3", "1", "2",
                "0", "7", "-0.312");
        modelList.add(mode2);
        ScoreCardModel mode3 = new ScoreCardModel("3", "", "RR",
                "3", "1", "2",
                "0", "7", "-0.312");
        modelList.add(mode3);
        ScoreCardModel model4 = new ScoreCardModel("4", "", "CSK",
                "3", "2", "1",
                "2", "9", "+0.632");
        modelList.add(model4);
        ScoreCardModel mode5 = new ScoreCardModel("5", "", "SRH",
                "3", "1", "2",
                "0", "7", "-0.312");
        modelList.add(mode5);
        ScoreCardModel model6 = new ScoreCardModel("6", "", "RCB",
                "3", "2", "1",
                "2", "9", "+0.632");
        modelList.add(model6);
        ScoreCardModel mode7 = new ScoreCardModel("7", "", "KKR",
                "3", "1", "2",
                "0", "7", "-0.312");
        modelList.add(mode7);
        ScoreCardModel model8 = new ScoreCardModel("8", "", "PK",
                "3", "2", "1",
                "2", "9", "+0.632");
        modelList.add(model8);
        return modelList;
    }

    private void load() {
        new Load().execute("http://3.108.39.214/getStanding?id=" + sid);
    }

    private void update() {
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScoreCardAdapter(getContext(), scoreCardModelList);
        recyclerView.setAdapter(adapter);
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
                    JSONArray data = object.getJSONObject("data").getJSONArray("teams");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);


                        try {
                            ScoreCardModel model = new ScoreCardModel("" + (i + 1), obj.getString("logoUrl"), obj.getString("shortName"),
                                    obj.getString("played"), obj.getString("won"), obj.getString("lost"),
                                    obj.getString("noResult"), obj.getString("points"), obj.getString("netRunRate"));

                            scoreCardModelList.add(model);

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