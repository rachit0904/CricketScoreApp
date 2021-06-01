package com.cricketexchange.project.ui.matchdetail.standings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.ScoreCardAdapter;
import com.cricketexchange.project.Models.ScoreCardModel;
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

public class TeamStanding extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;
    String sid = String.valueOf(2739);
    List<ScoreCardModel> scoreCardModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_standing, container, false);
        recyclerView = view.findViewById(R.id.pointsTableRv);
        progressBar = view.findViewById(R.id.progressBar);
        scoreCardModelList.clear();
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

    private void update() {
        progressBar.setVisibility(View.GONE);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //scoreCardModelList.clear();
        adapter = new ScoreCardAdapter(getContext(), scoreCardModelList);
        recyclerView.setAdapter(adapter);
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute("http://3.108.39.214/getStanding?id=" + sid);
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