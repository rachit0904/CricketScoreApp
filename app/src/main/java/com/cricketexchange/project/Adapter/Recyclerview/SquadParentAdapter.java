package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.TeamPlayersInfo;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SquadParentAdapter extends RecyclerView.Adapter<SquadParentAdapter.ViewHolder> {
    final Context context;
    final List<SquadModel> squadModelList;

    public SquadParentAdapter(Context context, List<SquadModel> squadModelList) {
        this.context = context;
        this.squadModelList = squadModelList;
    }

    @NonNull
    @Override
    public SquadParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamrv_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SquadParentAdapter.ViewHolder holder, int position) {
        SquadModel model = squadModelList.get(position);
        holder.SquadName.setText(model.getSquadFullname());
        if (model.getSquadLogoUrl().trim().length() != 0) {
            Picasso.get().load(model.getSquadLogoUrl()).into(holder.squadLogo);
        }

    }
    private String HOST = "";
    public void setHOST(String HOST) {
        this.HOST = HOST;
    }
    @Override
    public int getItemCount() {
        return squadModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView squadLogo;
        final TextView SquadName;
        CoordinatorLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            squadLogo = itemView.findViewById(R.id.teamlogo);
            SquadName = itemView.findViewById(R.id.teamname);
            layout = itemView.findViewById(R.id.teamlayout);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //TODO - send matchID seriesID to team details
            Intent intent = new Intent(context, TeamPlayersInfo.class);
            intent.putExtra("tid", squadModelList.get(getAdapterPosition()).getSquadID());
            intent.putExtra("tsn", squadModelList.get(getAdapterPosition()).getSquadName());
            intent.putExtra("tln", squadModelList.get(getAdapterPosition()).getSquadLogoUrl());
            intent.putExtra("tcl", squadModelList.get(getAdapterPosition()).getSquadColor());
            intent.putExtra("sname", squadModelList.get(getAdapterPosition()).getSquadFullname());
            intent.putExtra("HOST", HOST);
            context.startActivity(intent);
        }
    }
}
