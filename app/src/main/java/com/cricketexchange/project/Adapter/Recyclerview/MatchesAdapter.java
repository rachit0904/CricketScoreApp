package com.cricketexchange.project.Adapter.Recyclerview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    Context context;
    List<MatchesModel> matchesModels;
    List<MatchesChildModel> childModelList;

    public MatchesAdapter(Context context, List<MatchesModel> matchesModels, List<MatchesChildModel> childModelList) {
        this.context = context;
        this.matchesModels = matchesModels;
        this.childModelList = childModelList;
    }

    @NonNull
    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_rv_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder holder, int position) {
        MatchesModel data = matchesModels.get(position);
        holder.date.setText(data.getDate());
        List<MatchesChildModel> matchesChildModelList = new ArrayList<>();
        for (MatchesChildModel c : childModelList) {
            if (c.getStartDate().equalsIgnoreCase(data.getDate())) {
                matchesChildModelList.add(c);
                Log.e("FOR DATA", data.getDate());
                Log.e("FOR PREMIURE", c.getPremiure());
            }

        }
        //Logg();

        MatchesChildAdapter childAdapter = new MatchesChildAdapter(context, matchesChildModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.childRv.getContext(), LinearLayoutManager.VERTICAL, false);
        holder.childRv.setLayoutManager(layoutManager);
        holder.childRv.hasFixedSize();
        holder.childRv.setAdapter(childAdapter);
    }

    private void Logg() {
        for (int i = 0; i < childModelList.size(); i++) {
            Log.e("LOGG CHILDMOLDEL : " + i, childModelList.get(i).getTeam1());
        }
        for (int i = 0; i < matchesModels.size(); i++) {
            Log.e("LOGG MATCHMOLDEL : " + i, matchesModels.get(i).getDate());
        }
    }

    @Override
    public int getItemCount() {
        return matchesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        RecyclerView childRv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.matchDate);
            childRv = itemView.findViewById(R.id.matchCardRv);
        }
    }
}
