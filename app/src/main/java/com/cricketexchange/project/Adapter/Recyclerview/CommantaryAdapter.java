package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.CommentaryModal;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.matchdetail.commentary.Commentary;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommantaryAdapter extends RecyclerView.Adapter<CommantaryAdapter.ViewHolder> {
    Context context;
    List<CommentaryModal> commentaries;

    public CommantaryAdapter(Context context, List<CommentaryModal> commentaries) {
        this.context = context;
        this.commentaries = commentaries;
    }

    @NonNull
    @NotNull
    @Override
    public CommantaryAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.commentary_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommantaryAdapter.ViewHolder holder, int position) {
        CommentaryModal modal =commentaries.get(position);
        holder.ballType.setText(modal.getBallType());
        holder.commentaryText.setText(modal.getCommentaryText());
    }

    @Override
    public int getItemCount() {
        return commentaries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ballType,commentaryText;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ballType=itemView.findViewById(R.id.ballType);
            commentaryText=itemView.findViewById(R.id.commentaryText);
        }
    }
}
