package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.ScoreCardModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScoreCardAdapter extends RecyclerView.Adapter<ScoreCardAdapter.ViewHolder> {
    List<ScoreCardModel> scoreCardModelList;
    Context context;

    public ScoreCardAdapter(Context context, List<ScoreCardModel> scoreCardModelList) {
        this.scoreCardModelList = scoreCardModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scorerow_rv_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreCardAdapter.ViewHolder holder, int position) {
        ScoreCardModel modelList = scoreCardModelList.get(position);
        if (Integer.parseInt(modelList.getRowCount()) % 2 != 0) {
            holder.row.setBackground(holder.row.getResources().getDrawable(R.color.background));
        }
        holder.rowCount.setText(modelList.getRowCount());
        if (modelList.getTeamLogoURL().trim().length() != 0) {
            Picasso.get().load(modelList.getTeamLogoURL()).into(holder.teamLogo);
        }
        holder.teamName.setText(modelList.getTeamName());
        holder.p.setText(modelList.getPlayedMatches());
        holder.w.setText(modelList.getWonMatches());
        holder.l.setText(modelList.getLostMatches());
        holder.nr.setText(modelList.getNr());
        holder.pts.setText(modelList.getPts());
        holder.nrr.setText(modelList.getNrr());
        if (Float.parseFloat(modelList.getNrr()) > 0) {
            holder.nrr.setTextColor(holder.nrr.getResources().getColor(R.color.winDispColor));
        } else if (Float.parseFloat(modelList.getNrr()) < 0) {
            holder.nrr.setTextColor(holder.nrr.getResources().getColor(R.color.live));
        }
    }

    @Override
    public int getItemCount() {
        return scoreCardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView teamLogo;
        public TextView rowCount, teamName, p, w, l, nr, pts, nrr;
        public TableRow row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.scoreTableRow);
            rowCount = itemView.findViewById(R.id.rowCount);
            teamLogo = itemView.findViewById(R.id.teamLogo);
            teamName = itemView.findViewById(R.id.teamName);
            p = itemView.findViewById(R.id.playedMatches);
            w = itemView.findViewById(R.id.wonMatches);
            l = itemView.findViewById(R.id.lostMatches);
            nr = itemView.findViewById(R.id.NR);
            pts = itemView.findViewById(R.id.Pts);
            nrr = itemView.findViewById(R.id.NRR);
        }
    }
}
