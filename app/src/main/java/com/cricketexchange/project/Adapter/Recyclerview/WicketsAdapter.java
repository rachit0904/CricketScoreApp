package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.WicketsFallModel;
import com.cricketexchange.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WicketsAdapter extends RecyclerView.Adapter<WicketsAdapter.ViewHolder> {
    Context context;
    List<WicketsFallModel> list;

    public WicketsAdapter(Context context, List<WicketsFallModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public WicketsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fallofwickets_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WicketsAdapter.ViewHolder holder, int position) {
        WicketsFallModel model=list.get(position);
        holder.playerName.setText(model.getBatsmanName());
        holder.score.setText(model.getScore());
        holder.over.setText(model.getOver());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName,score,over;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.player);
            score=itemView.findViewById(R.id.score);
            over=itemView.findViewById(R.id.ovr);
        }
    }
}
