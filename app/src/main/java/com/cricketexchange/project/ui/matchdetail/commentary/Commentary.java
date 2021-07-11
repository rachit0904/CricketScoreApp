package com.cricketexchange.project.ui.matchdetail.commentary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.cricketexchange.project.Constants.Constants;
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

import static android.content.ContentValues.TAG;

public class Commentary extends Fragment {
    RecyclerView commentryRv;
    List<CommentaryModal> commentaries = new ArrayList<>();
    String mid, sid, inning;
    CommantaryAdapter adapter;
    String HOST;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commentary, container, false);
//        sid = getActivity().getIntent().getStringExtra("sid");
//        mid = getActivity().getIntent().getStringExtra("mid");
        progressBar = view.findViewById(R.id.progressBar);
        //2796S51038
        commentryRv = view.findViewById(R.id.commentary);
        sid = getActivity().getIntent().getStringExtra("sid");
        mid = getActivity().getIntent().getStringExtra("mid");
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        //SetOverBallScore();
        commentaries.clear();
        //SetOverOverview();
        commentryRv.hasFixedSize();
        commentryRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommantaryAdapter(getContext(), commentaries);
        commentryRv.setAdapter(adapter);
        load();
        return view;
    }

    private void load() {
                new LoadCommentary().execute(HOST + "getCommentary?sid=" + sid + "&mid=" + mid);
    }

    private void update() {
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
//                    if(Integer.parseInt(oover) < Integer.parseInt(lastover.getString("number")))
//                    {
//                        Toast.makeText(getContext(), oover+" over changed ", Toast.LENGTH_SHORT).show();
//                    }

                    JSONArray balls = lastover.getJSONArray("balls");
                    for (int j = 0; j < balls.length(); j++) {
                        JSONObject j_ball = balls.getJSONObject(j);


                        for (int i = 0; i < j_ball.getJSONArray("comments").length(); i++) {
                            commentaries.add(new CommentaryModal(j_ball.getJSONArray("comments").getJSONObject(i).getString("ballType"), j_ball.getJSONArray("comments").getJSONObject(i).getString("text")));
                            String s_runs = j_ball.getJSONArray("comments").getJSONObject(i).getString("runs");
                            if (s_runs.equals("") || s_runs == null) {
                                s_runs = "0";
                            }


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