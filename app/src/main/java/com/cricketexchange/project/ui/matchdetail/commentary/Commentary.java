package com.cricketexchange.project.ui.matchdetail.commentary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.CommantaryAdapter;
import com.cricketexchange.project.Models.CommentaryModal;
import com.cricketexchange.project.Models.OverBallScoreModel;
import com.cricketexchange.project.R;
import com.google.android.material.chip.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Commentary extends Fragment {
    TextView ovrs, runs, wkts;
    Chip b1, b2, b3, b4, b5, b6;
    RecyclerView commentryRv;
    List<CommentaryModal> commentaries = new ArrayList<>();
    String mid, sid, inning;
    CommantaryAdapter adapter;
    ProgressBar progressBar;
    String oover, oruns, owikts;
    ArrayList<OverBallScoreModel> overBallScoreModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commentary, container, false);
        sid = "2796";
        mid = "51038";
        progressBar = view.findViewById(R.id.progressBar);
        //2796S51038
        ovrs = view.findViewById(R.id.over);
        runs = view.findViewById(R.id.runs);
        wkts = view.findViewById(R.id.wkts);
        commentryRv = view.findViewById(R.id.commentary);
        b1 = view.findViewById(R.id.ball1);
        b2 = view.findViewById(R.id.ball2);
        b3 = view.findViewById(R.id.ball4);
        b4 = view.findViewById(R.id.ball4);
        b5 = view.findViewById(R.id.ball5);
        b6 = view.findViewById(R.id.ball6);
        //SetOverBallScore();
        //SetOverOverview();
        commentryRv.hasFixedSize();
        commentryRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommantaryAdapter(getContext(), commentaries);
        commentryRv.setAdapter(adapter);
        load();
        return view;
    }

    private void SetOverOverview() {
        ovrs.setText(oover);
        runs.setText(oruns);
        wkts.setText(owikts);
    }

    private int getBallColor(Boolean isWkt) {
        return (isWkt) ? R.color.live : android.R.color.holo_green_dark;
    }

    private void SetOverBallScore(String ballss, boolean iswikt, String runss) {
        Boolean isWkt = iswikt;
        int ball = Integer.parseInt(ballss); //get current ball
        int color = getBallColor(isWkt); //check isWkt to get ball color
        AtomicReference<String> runs = new AtomicReference<>(runss); //get runs
        if (runs.get().equals("6") || runs.get().equals("4")) {
            color = android.R.color.holo_blue_dark;
        }
        if ((isWkt)) {
            runs.set("W");
        }
        switch (ball) {
            case 1: {
                b1.setText(runs.get());
                b1.setChipBackgroundColorResource(color);
                b2.setChipBackgroundColorResource(R.color.background);
                b3.setChipBackgroundColorResource(R.color.background);
                b4.setChipBackgroundColorResource(R.color.background);
                b5.setChipBackgroundColorResource(R.color.background);
                b6.setChipBackgroundColorResource(R.color.background);
                break;
            }
            case 2: {
                b2.setText(runs.get());
                b2.setChipBackgroundColorResource(color);
                break;
            }
            case 3: {
                b3.setText(runs.get());
                b3.setChipBackgroundColorResource(color);
                break;
            }
            case 4: {
                b4.setText(runs.get());
                b4.setChipBackgroundColorResource(color);
                break;
            }
            case 5: {
                b5.setText(runs.get());
                b5.setChipBackgroundColorResource(color);
                break;
            }
            case 6: {
                b6.setText(runs.get());
                b6.setChipBackgroundColorResource(color);
                break;
            }

        }
    }


    private List<CommentaryModal> getData() {
        List<CommentaryModal> list = new ArrayList<>();
        CommentaryModal modal = new CommentaryModal("Legit OffBat !", "Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal);
        CommentaryModal modal2 = new CommentaryModal("Legit OffBat !", "Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal2);
        CommentaryModal modal3 = new CommentaryModal("Legit OffBat !", "Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal3);
        CommentaryModal modal4 = new CommentaryModal("Legit OffBat !", "Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal4);
        return list;
    }

    private void load() {
        // new LoadHighlight().execute("http://3.108.39.214/getCommentary?sid=" + sid + "&mid=" + mid);

        new LoadCommentary().execute("http://3.108.39.214/getCommentary?sid=" + sid + "&mid=" + mid);

    }

    private class LoadHighlight extends AsyncTask<String, Integer, Long> {

        @Override
        protected Long doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject object = new JSONObject(response.body().string());
                    inning = String.valueOf(object.getJSONObject("data").getJSONObject("meta").getInt("currentInningsId"));
                }
            } catch (JSONException | IOException jsonException) {
                jsonException.printStackTrace();
            }


            return (long) 513;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
        }
    }

    private void update() {
        SetOverOverview();
        for (int i = 0; i < overBallScoreModels.size(); i++) {

            SetOverBallScore(overBallScoreModels.get(i).getBallnumber(), overBallScoreModels.get(i).isIswicket(), overBallScoreModels.get(i).getBallrun());
        }

        progressBar.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }


    private class LoadCommentary extends AsyncTask<String, Integer, Long> {

        @Override
        protected Long doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray Innings = object.getJSONObject("data").getJSONObject("commentary").getJSONArray("innings");
                    JSONObject obj = Innings.getJSONObject(0);
                    String innigid = obj.getString("id");

                    JSONObject lastover = obj.getJSONArray("overs").getJSONObject(0);
                    JSONObject overSummary = lastover.getJSONObject("overSummary");
                    oover = lastover.getString("number");
                    oruns = overSummary.getString("runsConcededinOver");
                    owikts = overSummary.getString("wicketsTakeninOver");

                    JSONArray balls = lastover.getJSONArray("balls");
                    for (int j = 0; j < balls.length(); j++) {
                        JSONObject j_ball = balls.getJSONObject(j);


                        for (int i = 0; i < j_ball.getJSONArray("comments").length(); i++) {
                            commentaries.add(new CommentaryModal(j_ball.getJSONArray("comments").getJSONObject(i).getString("ballType"), j_ball.getJSONArray("comments").getJSONObject(i).getString("text")));
                            String s_runs = j_ball.getJSONArray("comments").getJSONObject(i).getString("runs");
                            if (s_runs.equals("") || s_runs == null) {
                                s_runs = "0";
                            }
                            Log.e("s_runs", s_runs);
                            overBallScoreModels.add(new OverBallScoreModel(j_ball.getString("ballNumber"), s_runs, j_ball.getJSONArray("comments").getJSONObject(i).getBoolean("isFallOfWicket")));


                        }


                    }


                }
            } catch (JSONException | IOException jsonException) {
                jsonException.printStackTrace();
            }
            return (long) 513;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update();

        }

    }


}