package com.cricketexchange.project.Adapter.Recyclerview;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchesChildAdapter extends RecyclerView.Adapter<MatchesChildAdapter.ViewHolder> {
    List<MatchesChildModel> childModelList;

    public MatchesChildAdapter(List<MatchesChildModel> childModelList) {
        this.childModelList = childModelList;
    }

    @NonNull
    @Override
    public MatchesChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_rv_childcard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesChildAdapter.ViewHolder holder, int position) {
        MatchesChildModel childModel=childModelList.get(position);
        //TODO - picassoo setup for loading team symbol
        holder.league.setText(childModel.getPremiure());
        holder.team1.setText(childModel.getTeam1());
        holder.team2.setText(childModel.getTeam2());
        if (!childModel.getStartTime().isEmpty()) {
            holder.startTime.setText(childModel.getStartTime());
            holder.winTeam.setVisibility(View.GONE);
            holder.status.setVisibility(View.GONE);
            holder.startTime.setVisibility(View.VISIBLE);
            holder.startTitle.setVisibility(View.VISIBLE);
        }else if(childModel.getStatus().equals("Live")){
            holder.winTeam.setText(childModel.getWinTeamName());
            holder.status.setText(childModel.getStatus());
            holder.winTeam.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setTextColor(Color.parseColor("#FF4747"));
            holder.startTime.setVisibility(View.GONE);
            holder.startTitle.setVisibility(View.GONE);
        }else if(!childModel.getStatus().isEmpty()){
            holder.winTeam.setVisibility(View.VISIBLE);
            holder.winTeam.setText(childModel.getWinTeamName());
            holder.winTeam.setTextColor(Color.parseColor("#C7CC35"));
            holder.status.setVisibility(View.GONE);
            holder.startTime.setVisibility(View.GONE);
            holder.startTitle.setVisibility(View.GONE);
        }else if(!childModel.getStatus().equals("Live")){
            holder.winTeam.setText(childModel.getWinTeamName());
            holder.winTeam.setTextColor(Color.parseColor("#C7CC35"));
            holder.status.setTextColor(Color.parseColor("#7BCC35"));
            holder.winTeam.setVisibility(View.VISIBLE);
            holder.status.setVisibility(View.VISIBLE);
            holder.startTime.setVisibility(View.GONE);
            holder.startTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return childModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView team1,team2,status,winTeam,startTime,startTitle,league;
        ImageView team1Icon,team2Icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            league=itemView.findViewById(R.id.premiereName);
            team1=itemView.findViewById(R.id.team1Name);
            team1Icon=itemView.findViewById(R.id.team1Icon);
            team2=itemView.findViewById(R.id.team2Name);
            team2Icon=itemView.findViewById(R.id.team2Icon);
            status=itemView.findViewById(R.id.status);
            startTime=itemView.findViewById(R.id.startTime);
            startTitle=itemView.findViewById(R.id.startStatus);
            winTeam=itemView.findViewById(R.id.statusCaption);
        }
    }
}
