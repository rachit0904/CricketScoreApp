package com.cricketexchange.project.Adapter.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingSeriesChildAdapter  extends RecyclerView.Adapter<UpcomingSeriesChildAdapter.ViewHolder> {
    List<SeriesModel> modelList=new ArrayList<>();

    public UpcomingSeriesChildAdapter(List<SeriesModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public UpcomingSeriesChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.upcomingseries_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingSeriesChildAdapter.ViewHolder holder, int position) {
        SeriesModel seriesModel=modelList.get(position);
        holder.seriesName.setText(seriesModel.getSeriesName());
        holder.duration.setText(seriesModel.getDuration());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView seriesName,duration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seriesName=itemView.findViewById(R.id.upcomingSeriesName);
            duration=itemView.findViewById(R.id.duration);
        }
    }
}
