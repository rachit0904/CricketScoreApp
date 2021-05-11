package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Models.MoreModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.ViewHolder> {


    ArrayList<MoreModel> list;
    Context context;

    public MoreAdapter(ArrayList<MoreModel> mRecyclerViewItems, Context mContext) {
        this.context = mContext;
        this.list = mRecyclerViewItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_more_lv, parent, false);
        return new MoreAdapter.ViewHolder(menuItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoreAdapter.ViewHolder menuItemHolder = holder;
        MoreModel moreModel = list.get(position);

        Picasso.get().load(moreModel.getMainincon()).into(holder.logo);
        holder.title.setText(moreModel.getText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo, seclogo;
        TextView title;

        public ViewHolder(@NonNull View v) {
            super(v);
            logo = v.findViewById(R.id.logo);
            seclogo = v.findViewById(R.id.next);
            title = v.findViewById(R.id.name);

        }
    }
}
