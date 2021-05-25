package com.cricketexchange.project.Adapter.Recyclerview;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.cricketexchange.project.Activity.MatchDetails;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.R;

import java.util.List;

public class MatchesChildAdapter extends RecyclerView.Adapter<MatchesChildAdapter.ViewHolder> {
    Context context;
    List<MatchesChildModel> childModelList;

    public MatchesChildAdapter(Context context, List<MatchesChildModel> childModelList) {
        this.context = context;
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
        //TODO - picassoo setup for loading team1,2 symbol
        holder.league.setText(childModel.getPremiure());
        holder.team1.setText(childModel.getTeam1());
        holder.team2.setText(childModel.getTeam2());
        String status=childModel.getStatus();
        switch(status){
            case "INPROGRESS":{
                String t1=childModel.getTeam1score()+"  ("+childModel.getTeam1over()+")";
                String t2=childModel.getTeam2score()+"  ("+childModel.getTeam2over()+")";
                holder.t1score.setText(t1);
                holder.t2score.setText(t2);
                holder.matchSummery.setText(childModel.getMatchSummery());
                break;
            } case "COMPLETED":{
                holder.liveIcon.setVisibility(View.GONE);
                holder.status.setTextColor(context.getColor(R.color.winDispColor));
                holder.status.setText(childModel.getWinTeamName()+" Won");
                holder.t1score.setText(childModel.getTeam1score()+"  ("+childModel.getTeam1over()+")");
                holder.t2score.setText(childModel.getTeam2score()+"  ("+childModel.getTeam2over()+")");
                holder.matchSummery.setText(childModel.getMatchSummery());
                break;
            } case "UPCOMING":{
                holder.layout.setVisibility(View.GONE);
                holder.t1score.setVisibility(View.INVISIBLE);
                holder.t2score.setVisibility(View.INVISIBLE);
                holder.matchSummery.setVisibility(View.GONE);
                holder.startTimeLayout.setVisibility(View.VISIBLE);
                holder.startTime.setText("14:00");
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return childModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView team1,team2,status, matchSummery,startTime,league,t1score,t2score;
        LinearLayout startTimeLayout;
        RelativeLayout layout;
        RelativeLayout matchCard;
        ImageView team1Icon,team2Icon;
        LottieAnimationView liveIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            matchCard=itemView.findViewById(R.id.matchCard);
            matchCard.setOnClickListener(this);
            layout=itemView.findViewById(R.id.layout2);
            league=itemView.findViewById(R.id.premiereName);
            team1=itemView.findViewById(R.id.team1Name);
            liveIcon=itemView.findViewById(R.id.isLive);
            t1score=itemView.findViewById(R.id.team1Score);
            t2score=itemView.findViewById(R.id.team2Score);
            team1Icon=itemView.findViewById(R.id.team1Icon);
            team2=itemView.findViewById(R.id.team2Name);
            team2Icon=itemView.findViewById(R.id.team2Icon);
            status=itemView.findViewById(R.id.status);
            startTime=itemView.findViewById(R.id.startTime);
            startTimeLayout=itemView.findViewById(R.id.startStat);
            matchSummery =itemView.findViewById(R.id.statusCaption);
        }

        @Override
        public void onClick(View v) {
            //TODO pass intent mID sId
            Intent intent=new Intent(context, MatchDetails.class);
            context.startActivity(intent);
        }
    }
}
