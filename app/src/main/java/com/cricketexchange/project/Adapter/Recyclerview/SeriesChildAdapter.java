package com.cricketexchange.project.Adapter.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.R;

import java.util.List;

public class SeriesChildAdapter extends RecyclerView.Adapter<SeriesChildAdapter.ViewHolder> {
    List<SeriesModel> data;

    public SeriesChildAdapter(List<SeriesModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SeriesChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.squadmembername_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesChildAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
