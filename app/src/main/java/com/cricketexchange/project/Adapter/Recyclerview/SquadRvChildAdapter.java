package com.cricketexchange.project.Adapter.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;

import java.util.List;

public class SquadRvChildAdapter extends RecyclerView.Adapter<SquadRvChildAdapter.ViewHolder> {
    List<SeriesModel> list;

    public SquadRvChildAdapter(List<SeriesModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SquadRvChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.squadmembername_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SquadRvChildAdapter.ViewHolder holder, int position) {
        SeriesModel model=list.get(position);
        holder.playerName.setText(model.getSeriesName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.squadMemberName);
        }
    }
}
