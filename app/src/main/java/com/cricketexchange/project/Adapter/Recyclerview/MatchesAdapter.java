package com.cricketexchange.project.Adapter.Recyclerview;

import android.app.Activity;
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
    Activity activity;
    List<MatchesModel> matchesModels;

    public MatchesAdapter(List<MatchesModel> matchesModels) {
        this.matchesModels = matchesModels;
    }

    @NonNull
    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_rv_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder holder, int position) {
        MatchesModel data=matchesModels.get(position);
        holder.date.setText(data.getDate());
        List<MatchesChildModel> childModelList=new ArrayList<>();
        MatchesChildModel childModel=new MatchesChildModel("Mumbai Indians","Chennai Super Kings","Indian Premiure League","","","03:15pm","","");
        childModelList.add(childModel);
        MatchesChildModel childModel2=new MatchesChildModel("Mumbai Indians","Delhi Capitals","Indian Premiure League","24 runs in 6 balls for DC","Live","","","");
        childModelList.add(childModel2);
        MatchesChildModel childModel3=new MatchesChildModel("Rajasthan Royals","Delhi Capitals","Indian Premiure League","DC won by 10 balls and 5 wkts","DC Won","","","");
        childModelList.add(childModel3);
        MatchesChildModel childModel4=new MatchesChildModel("Rajasthan Royals","Delhi Capitals","Indian Premiure League","DC won by 10 balls and 5 wkts","","","","");
        childModelList.add(childModel4);
        MatchesChildAdapter childAdapter=new MatchesChildAdapter(childModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.childRv.getContext(), LinearLayoutManager.VERTICAL, false);
        holder.childRv.setLayoutManager(layoutManager);
        holder.childRv.hasFixedSize();
        holder.childRv.setAdapter(childAdapter);
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
            date=itemView.findViewById(R.id.matchDate);
            childRv=itemView.findViewById(R.id.matchCardRv);
        }
    }
}
