package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.NewsDetailsActivity;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsNormalAdapter extends RecyclerView.Adapter<NewsNormalAdapter.ViewHolder> {
    String HOST = "";

    // An Activity's Context.
    private Context mContext;

    // The list of Native ads and menu items.
    private ArrayList<NewsModel> mRecyclerViewItems;

    public NewsNormalAdapter(Context mContext, ArrayList<NewsModel> mRecyclerViewItems) {
        this.mContext = mContext;
        this.mRecyclerViewItems = mRecyclerViewItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View menuItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_news_grid_rv, parent, false);
        return new NewsNormalAdapter.ViewHolder(menuItemLayoutView);
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsNormalAdapter.ViewHolder menuItemHolder = holder;
        NewsModel menuItem = (NewsModel) mRecyclerViewItems.get(position); //content holder
        if (menuItem != null) {
            (menuItemHolder).secondarytitle.setText(menuItem.getSecondarytitle());
            Picasso.get().load(menuItem.getPosterurl()).into((menuItemHolder).poster);
            (menuItemHolder).time.setText(menuItem.getTime());
            menuItemHolder.card.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                intent.putExtra("id", menuItem.getId());
                intent.putExtra("title", menuItem.getMaintitle());
                intent.putExtra("imageposter", menuItem.getPosterurl());
                intent.putExtra("html", menuItem.getDescription());
                intent.putExtra("HOST", HOST);
                mContext.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView secondarytitle, time;
        ImageView poster;
        CardView card;

        public ViewHolder(@NonNull View v) {
            super(v);

            secondarytitle = v.findViewById(R.id.secondarytitle);
            time = v.findViewById(R.id.time);
            poster = v.findViewById(R.id.poster);
            card = v.findViewById(R.id.card);
        }
    }
}
