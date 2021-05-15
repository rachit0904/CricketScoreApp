/*
 * Copyright (C) 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.NewsDetailsActivity;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * The {@link NewsRecyclerAdapter} class.
 * <p>The adapter provides access to the items in the {@link MenuItemViewHolder}
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;

    // An Activity's Context.
    private final Context mContext;

    // The list of Native ads and menu items.
    private final List<Object> mRecyclerViewItems;

    public NewsRecyclerAdapter(Context context, List<Object> recyclerViewItems) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
    }

    /**
     * The {@link MenuItemViewHolder} class.
     * Provides a reference to each view in the menu item view.
     */
    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView maintitle, secondarytitle, time;
        ImageView poster;
        CardView card;

        MenuItemViewHolder(View v) {
            super(v);
            maintitle = v.findViewById(R.id.mainTitle);
            secondarytitle = v.findViewById(R.id.secondarytitle);
            time = v.findViewById(R.id.time);
            poster = v.findViewById(R.id.poster);
            card = v.findViewById(R.id.card);
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = mRecyclerViewItems.get(position);
        if (recyclerViewItem instanceof UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return MENU_ITEM_VIEW_TYPE;
    }

    /**
     * Creates a new view for a menu item view or a Native ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
//            case UNIFIED_NATIVE_AD_VIEW_TYPE:
//                View unifiedNativeLayoutView = LayoutInflater.from(
//                        viewGroup.getContext()).inflate(R.layout.ad_unified,
//                        viewGroup, false);
//                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case MENU_ITEM_VIEW_TYPE:
                // Fall through.
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.card_news_rv, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
        }
    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * Native ad view. This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                UnifiedNativeAd nativeAd = (UnifiedNativeAd) mRecyclerViewItems.get(position);
//                populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
                break;
            case MENU_ITEM_VIEW_TYPE:


            default:
                MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
                NewsModel menuItem = (NewsModel) mRecyclerViewItems.get(position);//content holder
                if (menuItem != null) {
                    (menuItemHolder).maintitle.setText(menuItem.getMaintitle());
                    (menuItemHolder).secondarytitle.setText(menuItem.getSecondarytitle());
                    Picasso.get().load(menuItem.getPosterurl()).into((menuItemHolder).poster);
                    (menuItemHolder).time.setText(menuItem.getTime());
                    menuItemHolder.card.setOnClickListener(view -> {
                        Intent intent = new Intent(mContext, NewsDetailsActivity.class);
                        intent.putExtra("id", menuItem.getId());
                        intent.putExtra("title", menuItem.getMaintitle());
                        intent.putExtra("imageposter", menuItem.getPosterurl());
                        intent.putExtra("html", menuItem.getDescription());

                        mContext.startActivity(intent);
                    });
                }
                break;

        }
    }

//    private void populateNativeAdView(UnifiedNativeAd nativeAd,
//                                      UnifiedNativeAdView adView) {
//        // Some assets are guaranteed to be in every UnifiedNativeAd.
//        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
//        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
//        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//
//        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
//        // check before trying to display them.
//        NativeAd.Image icon = nativeAd.getIcon();
//
//        if (icon == null) {
//            adView.getIconView().setVisibility(View.INVISIBLE);
//        } else {
//            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
//            adView.getIconView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getPriceView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }
//
//        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getStoreView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }
//
//        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) adView.getStarRatingView())
//                    .setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }
//
//        // Assign native ad object to the native view.
//        adView.setNativeAd(nativeAd);
//    }
}
