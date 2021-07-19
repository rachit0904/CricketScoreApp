package com.cricketexchange.project.Adapter.Recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.SessionsDataModel;
import com.cricketexchange.project.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SessionRecyclerAdapter extends RecyclerView.Adapter<SessionRecyclerAdapter.ViewHolder> {
    List<SessionsDataModel> sessionsData=new ArrayList<>();

    public SessionRecyclerAdapter(List<SessionsDataModel> sessionsData) {
        this.sessionsData = sessionsData;
    }

    @NonNull
    @NotNull
    @Override
    public SessionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.session_data_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SessionRecyclerAdapter.ViewHolder holder, int position) {
            SessionsDataModel model=sessionsData.get(position);
            holder.overStatus.setText(model.getOverStatus());
            holder.overRuns.setText(model.getOverRuns());
            holder.op.setText(model.getOp());
            holder.min.setText(model.getMin());
            holder.max.setText(model.getMax());
            holder.yes.setText(model.getYes());
            holder.no.setText(model.getNo());
    }

    @Override
    public int getItemCount() {
        return sessionsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView overStatus;
        final TextView overRuns;
        final TextView op;
        final TextView min;
        final TextView max;
        final TextView yes;
        final TextView no;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            overStatus=itemView.findViewById(R.id.oversStatus);
            op=itemView.findViewById(R.id.sessionOp);
            min=itemView.findViewById(R.id.sessionMin);
            max=itemView.findViewById(R.id.sessionMax);
            yes=itemView.findViewById(R.id.sessionYes);
            no=itemView.findViewById(R.id.sessionNo);
            overRuns=itemView.findViewById(R.id.SessionOverRunsData);
        }
    }
}
