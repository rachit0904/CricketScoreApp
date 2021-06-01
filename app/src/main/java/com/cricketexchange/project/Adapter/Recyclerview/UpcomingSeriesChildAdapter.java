package com.cricketexchange.project.Adapter.Recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.SeriesDetail;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingSeriesChildAdapter extends RecyclerView.Adapter<UpcomingSeriesChildAdapter.ViewHolder> {
    List<SeriesModel> modelList = new ArrayList<>();
    Context context;

    public UpcomingSeriesChildAdapter(List<SeriesModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public UpcomingSeriesChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcomingseries_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingSeriesChildAdapter.ViewHolder holder, int position) {
        SeriesModel seriesModel = modelList.get(position);
        holder.seriesName.setText(seriesModel.getSeriesName());
        holder.duration.setText(seriesModel.getDuration());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView seriesName, duration;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.matchcard);
            seriesName = itemView.findViewById(R.id.upcomingSeriesName);
            duration = itemView.findViewById(R.id.duration);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == cardView) {
                //pass series id

                Intent intent = new Intent(context, SeriesDetail.class);
                intent.putExtra("sid", modelList.get(getAdapterPosition()).getSid());
                intent.putExtra("name", modelList.get(getAdapterPosition()).getSeriesName());
              //  Log.e("name", modelList.get(getAdapterPosition()).getSeriesName());
                context.startActivity(intent);

            }
        }
    }
}
