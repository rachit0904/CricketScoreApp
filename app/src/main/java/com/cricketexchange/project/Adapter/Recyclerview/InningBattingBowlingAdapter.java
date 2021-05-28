package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.cricketexchange.project.Models.BattingInningModal;
import com.cricketexchange.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InningBattingBowlingAdapter extends RecyclerView.Adapter<InningBattingBowlingAdapter.ViewHolder> {
    Context context;
    List<BattingInningModal> list;

    public InningBattingBowlingAdapter(Context context, List<BattingInningModal> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public InningBattingBowlingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inningrv_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InningBattingBowlingAdapter.ViewHolder holder, int position) {
        BattingInningModal modal=list.get(position);
        holder.playerName.setText(modal.getPlayerName());
        if(!modal.getOutBy().isEmpty()){
            holder.outBy.setText(modal.getOutBy());
        }else{
            holder.outBy.setVisibility(View.GONE);
        }
        holder.c1.setText(modal.getR());
        holder.c2.setText(modal.getB());
        holder.c3.setText(modal.getFour());
        holder.c4.setText(modal.getSix());
        holder.c5.setText(modal.getSr());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName,outBy,c1,c2,c3,c4,c5;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.batsmanName);
            outBy=itemView.findViewById(R.id.outBy);
            c1=itemView.findViewById(R.id.v1);
            c2=itemView.findViewById(R.id.v2);
            c3=itemView.findViewById(R.id.v3);
            c4=itemView.findViewById(R.id.v4);
            c5=itemView.findViewById(R.id.v5);
        }
    }
}
