package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.PartnershipsModal;
import com.cricketexchange.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PartnershipsAdapter extends RecyclerView.Adapter<PartnershipsAdapter.ViewHolder> {
    Context context;
    List<PartnershipsModal> partnershipsModalList;

    public PartnershipsAdapter(Context context, List<PartnershipsModal> partnershipsModalList) {
        this.context = context;
        this.partnershipsModalList = partnershipsModalList;
    }

    @NonNull
    @NotNull
    @Override
    public PartnershipsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.partnership_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PartnershipsAdapter.ViewHolder holder, int position) {
        PartnershipsModal modal=partnershipsModalList.get(position);
        holder.p1Name.setText(modal.getPlayer1());
        holder.p1Score.setText(modal.getPlayer1Runs()+"  ("+modal.getPlayer1Overs()+")");
        holder.p2Name.setText(modal.getPlayer2());
        holder.p2Score.setText(modal.getPlayer2Runs()+"  ("+modal.getPlayer2Overs()+")");
    }

    @Override
    public int getItemCount() {
        return partnershipsModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView p1Name,p2Name,p2Score,p1Score;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            p1Name=itemView.findViewById(R.id.p1);
            p1Score=itemView.findViewById(R.id.p1Score);
            p2Name=itemView.findViewById(R.id.p2);
            p2Score=itemView.findViewById(R.id.p2Score);
        }
    }
}
