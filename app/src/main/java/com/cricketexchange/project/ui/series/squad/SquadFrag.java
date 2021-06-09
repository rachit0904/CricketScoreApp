package com.cricketexchange.project.ui.series.squad;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.SquadParentAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.SquadModel;
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

public class SquadFrag extends Fragment {
    RecyclerView recyclerView;
    SquadParentAdapter adapter;
    List<SquadModel> list = new ArrayList<>();
    String sid = "";
    String HOST;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_squad, container, false);
        recyclerView = view.findViewById(R.id.squadRv);
        sid = requireActivity().getIntent().getStringExtra("sid");
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        list.clear();
        progressBar = view.findViewById(R.id.progressBar);
        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute(HOST + "AllTeamsBySID?id=" + sid);
    }


    private void update() {
        progressBar.setVisibility(View.GONE);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SquadParentAdapter(getContext(), list);
        adapter.setHOST(HOST);
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
                    JSONArray data = object.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);


                        try {
                            SquadModel model = new SquadModel(obj.getString("id"), obj.getString("shortName"), obj.getString("name"), obj.getString("logoUrl"), obj.getString("teamColour"));
                            list.add(model);
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