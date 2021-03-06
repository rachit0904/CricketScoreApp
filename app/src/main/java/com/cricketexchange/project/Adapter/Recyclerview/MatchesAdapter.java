package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
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
    final Context context;
    final List<MatchesModel> matchesModels;
    final List<MatchesChildModel> childModelList;

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
        String months[] = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};
        MatchesModel data = matchesModels.get(position);
        String date[] = data.getDate().split("-");
        String sD = (date[0] + " " + months[Integer.parseInt(date[1]) - 1] + "," + date[2]);
        List<MatchesChildModel> matchesChildModelList = new ArrayList<>();
        for (MatchesChildModel c : childModelList) {
            if (c.getStartDate().equalsIgnoreCase(data.getDate())) {
                matchesChildModelList.add(c);
            }
        }
        if (matchesChildModelList.size() > 0) {
            holder.date.setText(sD);
            MatchesChildAdapter childAdapter = new MatchesChildAdapter(context, matchesChildModelList);
            childAdapter.setHOST(HOST);
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.childRv.getContext(), LinearLayoutManager.VERTICAL, false);
            holder.childRv.setLayoutManager(layoutManager);
            holder.childRv.hasFixedSize();
            holder.childRv.setAdapter(childAdapter);
        } else {
            holder.childRv.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return matchesModels.size();
    }

    String HOST;

    public void setHOST(String host) {
        HOST = host;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView date;
        final RecyclerView childRv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.matchDate);
            childRv = itemView.findViewById(R.id.matchCardRv);
        }
    }
}
