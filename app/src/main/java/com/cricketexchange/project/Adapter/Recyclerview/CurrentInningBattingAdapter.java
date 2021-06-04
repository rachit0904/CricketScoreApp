package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.battingCardModal;
import com.cricketexchange.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CurrentInningBattingAdapter extends RecyclerView.Adapter<CurrentInningBattingAdapter.ViewHolder> {
    Context context;
    List<battingCardModal> list=new ArrayList<>();

    public CurrentInningBattingAdapter(Context context, List<battingCardModal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public CurrentInningBattingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.currentbatting_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CurrentInningBattingAdapter.ViewHolder holder, int position) {
        battingCardModal modal=list.get(position);
        holder.playerName.setText(modal.getPlayerName());
        if(!modal.getRuns().isEmpty()) {
            holder.score.setText(modal.getRuns() + "  (" + modal.getOvers() + ")");
        }else{
            holder.score.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName,score;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.playerName);
            score=itemView.findViewById(R.id.scoreOrStatus);
        }
    }
}
