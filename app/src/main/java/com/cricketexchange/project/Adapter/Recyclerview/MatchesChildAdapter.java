package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.cricketexchange.project.Activity.MatchDetails;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchesChildAdapter extends RecyclerView.Adapter<MatchesChildAdapter.ViewHolder> {

    final Context context;
    final List<MatchesChildModel> childModelList;

    public MatchesChildAdapter(Context context, List<MatchesChildModel> childModelList) {
        this.context = context;
        this.childModelList = childModelList;
    }

    @NonNull
    @Override
    public MatchesChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_rv_childcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesChildAdapter.ViewHolder holder, int position) {
        MatchesChildModel childModel = childModelList.get(position);
        //TODO - picassoo setup for loading team1,2 symbol
        if (childModel.getTeam1Url().trim().length() != 0) {
            //Log.e("TEAM 1 URL", childModel.getTeam1Url());
            Picasso.get().load(childModel.getTeam1Url()).into(holder.team1Icon);
        }
        if (childModel.getTeam2Url().trim().length() != 0) {
            //Log.e("TEAM 2 URL",childModel.getTeam2Url());
            Picasso.get().load(childModel.getTeam2Url()).into(holder.team2Icon);
        }
        holder.league.setText(childModel.getPremiure());
        holder.team1.setText(childModel.getTeam1());
        holder.team2.setText(childModel.getTeam2());
        String status = childModel.getStatus();
        switch (status) {
            case "INPROGRESS": {
            }
            case "LIVE": {
                String t1score=childModel.getTeam1score();
                if(t1score.contains("&")){
                    String[] s1 =t1score.split("&");
                    t1score=s1[1];
                }
                String t2score=childModel.getTeam2score();
                if(t2score.contains("&")){
                    String[] s2 =t2score.split("&");
                    t2score=s2[1];
                }
                String t1over=childModel.getTeam1over();
                if(t1over.contains("&")){
                    String[] o1 =t1over.split("&");
                    t1over=o1[1];
                }
                String t2over=childModel.getTeam2over();
                if(t2over.contains("&")){
                    String[] o2 =t2over.split("&");
                    t2over=o2[1];
                }
                if (!t1score.isEmpty() || !t1over.isEmpty()) {
                    holder.t1score.setText(t1score + " (" + t1over + ")");
                }
                if (!t2score.equals("") && !t2over.equals("")) {
                    holder.t2score.setText(t2score + " (" + t2over + ")");
                }
                holder.matchSummery.setText(childModel.getMatchSummery());
                break;
            }
            case "COMPLETED": {
                String t1score=childModel.getTeam1score();
                if(t1score.contains("&")){
                    String[] s1 =t1score.split("&");
                    t1score=s1[1];
                }
                String t2score=childModel.getTeam2score();
                if(t2score.contains("&")){
                    String[] s2 =t2score.split("&");
                    t2score=s2[1];
                }
                String t1over=childModel.getTeam1over();
                if(t1over.contains("&")){
                    String[] o1 =t1over.split("&");
                    t1over=o1[1];
                }
                String t2over=childModel.getTeam2over();
                if(t2over.contains("&")){
                    String[] o2 =t2over.split("&");
                    t2over=o2[1];
                }
                if (childModel.getIsDraw().contains("false")) {
                    holder.liveIcon.setVisibility(View.GONE);
                    holder.status.setTextColor(context.getColor(R.color.winDispColor));
                    holder.status.setText(childModel.getWinTeamName() + " Won");
                    if (!t1score.isEmpty() || !t1over.isEmpty()) {
                        holder.t1score.setText(t1score + " (" + t1over + ")");
                    }
                    if (!t2score.equals("") && !t2over.equals("")) {
                        holder.t2score.setText(t2score + " (" + t2over + ")");
                    }
                    holder.matchSummery.setText(childModel.getMatchSummery());
                } else {
                    holder.liveIcon.setVisibility(View.GONE);
                    holder.status.setTextColor(context.getColor(R.color.winTeamName));
                    holder.status.setText("Match Drawn");
                    if (!t1score.isEmpty() || !t1over.isEmpty()) {
                        holder.t1score.setText(t1score + " (" + t1over + ")");
                    }
                    if (!t2score.equals("") && !t2over.equals("")) {
                        holder.t2score.setText(t2score + " (" + t2over + ")");
                    }
                    holder.matchSummery.setText(childModel.getMatchSummery());
                }
                break;
            }
            case "UPCOMING": {
                holder.layout.setVisibility(View.GONE);
                holder.t1score.setVisibility(View.GONE);
                holder.t2score.setVisibility(View.GONE);
                holder.matchSummery.setVisibility(View.GONE);
                holder.startTimeLayout.setVisibility(View.VISIBLE);
                holder.startTime.setText(childModel.getStartTime());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return childModelList.size();
    }
    private String HOST = "";
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView team1;
        final TextView team2;
        final TextView status;
        final TextView matchSummery;
        final TextView startTime;
        final TextView league;
        final TextView t1score;
        final TextView t2score;
        final LinearLayout startTimeLayout;
        final RelativeLayout layout;
        final RelativeLayout matchCard;
        final ImageView team1Icon;
        final ImageView team2Icon;
        final LottieAnimationView liveIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            matchCard = itemView.findViewById(R.id.matchCard);
            matchCard.setOnClickListener(this);
            layout = itemView.findViewById(R.id.layout2);
            league = itemView.findViewById(R.id.premiereName);
            team1 = itemView.findViewById(R.id.team1Name);
            liveIcon = itemView.findViewById(R.id.isLive);
            t1score = itemView.findViewById(R.id.team1Score);
            t2score = itemView.findViewById(R.id.team2Score);
            team1Icon = itemView.findViewById(R.id.team1Icon);
            team2 = itemView.findViewById(R.id.team2Name);
            team2Icon = itemView.findViewById(R.id.team2Icon);
            status = itemView.findViewById(R.id.status);
            startTime = itemView.findViewById(R.id.startTime);
            startTimeLayout = itemView.findViewById(R.id.startStat);
            matchSummery = itemView.findViewById(R.id.statusCaption);
        }

        @Override
        public void onClick(View v) {
            //TODO pass intent mID,sId
            Intent intent = new Intent(context, MatchDetails.class);
            String match = childModelList.get(getAdapterPosition()).getTeam1() + " vs " + childModelList.get(getAdapterPosition()).getTeam2();
            intent.putExtra("match", match);
            intent.putExtra("matchSumm", childModelList.get(getAdapterPosition()).getMatchSummery());
            intent.putExtra("status", childModelList.get(getAdapterPosition()).getStatus());
            intent.putExtra("startTime", childModelList.get(getAdapterPosition()).getStartTime());
            intent.putExtra("t1logo", childModelList.get(getAdapterPosition()).getTeam1Url());
            intent.putExtra("t2logo", childModelList.get(getAdapterPosition()).getTeam2Url());
            intent.putExtra("t1nme", childModelList.get(getAdapterPosition()).getTeam1());
            intent.putExtra("t2nme", childModelList.get(getAdapterPosition()).getTeam2());
            intent.putExtra("sid", childModelList.get(getAdapterPosition()).getsId());
            intent.putExtra("mid", childModelList.get(getAdapterPosition()).getmId());
            intent.putExtra("HOST", HOST);
            context.startActivity(intent);

        }
    }
}
