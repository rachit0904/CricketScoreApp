package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.TeamPlayersInfo;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TeamRecycleAdapter extends RecyclerView.Adapter<TeamRecycleAdapter.ViewHolder> {
    Context context;

    List<SquadModel> modelList;

    public TeamRecycleAdapter(Context context, List<SquadModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public TeamRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamrv_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamRecycleAdapter.ViewHolder holder, int position) {
        SquadModel model = modelList.get(position);

        String url = model.getSquadLogoUrl();
        if (url != null || url != "") {
            Picasso.get().load(url).into(holder.teamLogo);
        }
        holder.teamName.setText(model.getSquadFullname());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private String HOST = "";

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView teamLogo;
        TextView teamName;
        CoordinatorLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.teamlayout);
            itemView.setOnClickListener(this);
            teamLogo = itemView.findViewById(R.id.teamlogo);
            teamName = itemView.findViewById(R.id.teamname);
        }

        @Override
        public void onClick(View v) {
            //TODO - send matchID seriesID to team details
            Intent intent = new Intent(context, TeamPlayersInfo.class);
            intent.putExtra("tid", modelList.get(getAdapterPosition()).getSquadID());
            intent.putExtra("tsn", modelList.get(getAdapterPosition()).getSquadName());
            intent.putExtra("tln", modelList.get(getAdapterPosition()).getSquadLogoUrl());
            intent.putExtra("tcl", modelList.get(getAdapterPosition()).getSquadColor());
            intent.putExtra("sname", modelList.get(getAdapterPosition()).getSquadFullname());
            intent.putExtra("HOST", HOST);
            context.startActivity(intent);
        }
    }
}
