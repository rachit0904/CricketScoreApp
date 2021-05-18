package com.cricketexchange.project.Adapter.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class TeamRecycleAdapter  extends RecyclerView.Adapter<TeamRecycleAdapter.ViewHolder> {
    List<SquadModel> modelList=new ArrayList<>();

    public TeamRecycleAdapter(List<SquadModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public TeamRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.teamrv_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamRecycleAdapter.ViewHolder holder, int position) {
        SquadModel model=modelList.get(position);
        //TODO - picasso for setting team logo
        holder.teamName.setText(model.getSquadName());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView teamLogo;
        TextView teamName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamLogo=itemView.findViewById(R.id.teamlogo);
            teamName=itemView.findViewById(R.id.teamname);
        }
    }
}
