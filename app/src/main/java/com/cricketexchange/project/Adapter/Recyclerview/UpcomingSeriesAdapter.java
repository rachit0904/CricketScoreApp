package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.SeriesDetail;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.UpcomingSeriesModel;
import com.cricketexchange.project.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcomingmatches_rv_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingSeriesAdapter.ViewHolder holder, int position) {
        UpcomingSeriesModel matchesModel = list.get(position);
        String arr[] = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};
        List<SeriesModel> seriesModelList = new ArrayList<>();
        for (SeriesModel x : childSeriesModel) {
            if ((x.getStartDate()).equals((matchesModel.getDate()))) {
                String date=x.getStartDate();
                String d[]=date.split("-");
                holder.month.setText(arr[Integer.parseInt(d[0])-1]+" "+d[1]);
                seriesModelList.add(x);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.series.setLayoutManager(new LinearLayoutManager(holder.series.getContext()));
        UpcomingSeriesChildAdapter adapter = new UpcomingSeriesChildAdapter(seriesModelList, context);
        holder.series.hasFixedSize();
        holder.series.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView month;
        RecyclerView series;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.date);
            series = itemView.findViewById(R.id.upcomingMatchRv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SeriesDetail.class);
            intent.putExtra("sid", childSeriesModel.get(getAdapterPosition()).getSid());
            context.startActivity(intent);
        }
    }
}
