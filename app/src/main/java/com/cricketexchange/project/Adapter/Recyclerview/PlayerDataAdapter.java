package com.cricketexchange.project.Adapter.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.PlayersDataModel;
import com.cricketexchange.project.R;
import com.google.android.material.chip.Chip;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerDataAdapter extends RecyclerView.Adapter<PlayerDataAdapter.ViewHolder> {
    List<PlayersDataModel> list;

    public PlayerDataAdapter(List<PlayersDataModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PlayerDataAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.playercard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlayerDataAdapter.ViewHolder holder, int position) {
        PlayersDataModel model=list.get(position);
        holder.playerName.setText(model.getName());
        //TODO set player image
        holder.playerType.setText(model.getPlayerType());
        holder.battingStyle.setText(model.getBattingStyle());
        holder.bowlingStyle.setText(model.getBowlingStyle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Chip playerType,battingStyle,bowlingStyle;
        TextView playerName;
        ImageView playerImg,collapseBtn;
        RelativeLayout layout,layout2;
        Boolean flag=false;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            playerImg=itemView.findViewById(R.id.playerImg);
            collapseBtn=itemView.findViewById(R.id.showBtn);
            playerName=itemView.findViewById(R.id.playername);
            layout2=itemView.findViewById(R.id.playerInfo);
            layout=itemView.findViewById(R.id.rel1);
            playerType=itemView.findViewById(R.id.playerType);
            battingStyle=itemView.findViewById(R.id.battingStyle);
            bowlingStyle=itemView.findViewById(R.id.bowlingStyle);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==layout){
                if(flag){
                    collapseBtn.setBackgroundResource(R.drawable.showless);
                    layout2.setVisibility(View.GONE);
                    flag=false;
                }else{
                    layout2.setVisibility(View.VISIBLE);
                    collapseBtn.setBackgroundResource(R.drawable.showmore);
                    flag=true;
                }
            }
        }
    }
}
