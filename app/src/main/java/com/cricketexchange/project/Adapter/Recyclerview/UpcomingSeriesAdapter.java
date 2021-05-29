package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.UpcomingSeriesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingSeriesAdapter extends RecyclerView.Adapter<UpcomingSeriesAdapter.ViewHolder> {
    Context context;
    List<UpcomingSeriesModel> list;
    List<SeriesModel> childSeriesModel;

    public UpcomingSeriesAdapter(Context context, List<UpcomingSeriesModel> list, List<SeriesModel> childSeriesModel) {
        this.context = context;
        this.list = list;
        this.childSeriesModel = childSeriesModel;
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
        holder.month.setText(matchesModel.getDate());
        List<SeriesModel> seriesModelList=new ArrayList<>();
        for(SeriesModel x:childSeriesModel){
            if(x.getStartDate().equalsIgnoreCase(matchesModel.getDate()) ){
                seriesModelList.add(x);
            }
        }
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
