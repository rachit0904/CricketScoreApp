package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.UpcomingSeriesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingSeriesAdapter extends RecyclerView.Adapter<UpcomingSeriesAdapter.ViewHolder> {
    List<UpcomingSeriesModel> list=new ArrayList<>();
    Context context;
    public UpcomingSeriesAdapter(Context context,List<UpcomingSeriesModel> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public UpcomingSeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.upcomingmatches_rv_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingSeriesAdapter.ViewHolder holder, int position) {
        UpcomingSeriesModel matchesModel=list.get(position);
        holder.month.setText(matchesModel.getMatchDate());
        List<SeriesModel> seriesModelList=new ArrayList<>();
        SeriesModel model=new SeriesModel("T20 blast 2021","2 may to 3 june");
        seriesModelList.add(model);
        SeriesModel model2=new SeriesModel("Australia Test Series","21 jan to 15 feb");
        seriesModelList.add(model2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.series.setLayoutManager(new LinearLayoutManager(holder.series.getContext()));
        UpcomingSeriesChildAdapter adapter=new UpcomingSeriesChildAdapter(seriesModelList,context);
        holder.series.hasFixedSize();
        holder.series.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView month;
        RecyclerView series;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month=itemView.findViewById(R.id.date);
            series=itemView.findViewById(R.id.upcomingMatchRv);
        }
    }
}
