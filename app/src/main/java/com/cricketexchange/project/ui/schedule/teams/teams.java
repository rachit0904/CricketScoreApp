package com.cricketexchange.project.ui.schedule.teams;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.TeamRecycleAdapter;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class teams extends Fragment {
    RecyclerView recyclerView;
    SearchView searchView;
    TeamRecycleAdapter adapter;
    List<SquadModel> list = new ArrayList<>();//list
    List<SquadModel> filterd = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        recyclerView = view.findViewById(R.id.team);
        progressBar = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.search);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                filterd.clear();
                if (query.equals("")) {
                    filterd.addAll(list);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getSquadFullname().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                            filterd.add(list.get(i));
                        }
                    }

                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                filterd.clear();
                if (newText.equals("")) {
                    filterd.addAll(list);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getSquadFullname().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))) {
                            filterd.add(list.get(i));
                        }
                    }

                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
        load();
        return view;
    }


    private void load() {
        new Load().execute(Constants.HOST + "getAllTeams");
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void update() {

        filterd.addAll(list);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeamRecycleAdapter(getContext(), filterd);
        recyclerView.setAdapter(adapter);
        searchView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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

                        SquadModel model = new SquadModel(obj.getString("id"), obj.getString("shortName"), obj.getString("name"), obj.getString("logoUrl"), obj.getString("teamColour"));


                        list.add(model);


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

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onPostExecute(Long result) {
            update();
        }
    }

}