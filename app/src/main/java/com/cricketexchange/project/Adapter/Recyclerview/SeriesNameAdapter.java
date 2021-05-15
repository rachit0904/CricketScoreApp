package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.Activity.SeriesDetail;

import java.util.ArrayList;

public class SeriesNameAdapter extends RecyclerView.Adapter<SeriesNameAdapter.ViewHolder> {

    Context context;
    ArrayList<SeriesModel> seriesData;

    public SeriesNameAdapter(Context context, ArrayList<SeriesModel> seriesData) {
        this.context = context;
        this.seriesData = seriesData;
    }

    @NonNull
    @Override
    public SeriesNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.series_rv_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesNameAdapter.ViewHolder holder, int position) {
        SeriesModel model=seriesData.get(position);
        holder.textView.setText(model.getSeriesName());
    }

    @Override
    public int getItemCount() {
        return seriesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView more;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.seriesName);
            more=itemView.findViewById(R.id.viewSeriesDetail);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, SeriesDetail.class));
        }
    }
}
