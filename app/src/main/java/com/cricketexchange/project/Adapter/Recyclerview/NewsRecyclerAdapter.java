package com.cricketexchange.project.Adapter.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Activity.NewsDetailsActivity;
import com.cricketexchange.project.Ads.AdTemplateViewHolder;
import com.cricketexchange.project.Ads.AdUnifiedListening;
import com.cricketexchange.project.Ads.AdsManager;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MediaContent;
import com.google.android.gms.ads.MuteThisAdListener;
import com.google.android.gms.ads.MuteThisAdReason;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;

    String HOST = "";
    private ArrayList<Object> data = new ArrayList<>();


    public NewsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Object> emp) {
        this.data.addAll(emp);
    }


    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    int count = 0;
    private static final int AD_COUNT = 2;
    static int OFFSET = 5;
    private static final int TYPE_AD = 0;
    private static final int TYPE_NORMAL = 1;

    private ArrayList<UnifiedNativeAd> ads = new ArrayList<>();

    public void MixData() {

        addFakeAds();
        List<Object> o = new ArrayList<>();
        int num = 0;
        for (int i = 0; i < data.size(); i++) {
            if (num + OFFSET == i) {
                //REMOVE +1
                num += OFFSET;
                int x = new Random().nextInt(ads.size());
                o.add(ads.get(x));
                //                    //REMOVE THIS CONTINUE NOT TO SKIP INSERT DATA
                //continue;
            }
            o.add(data.get(i));
        }
        data.clear();
        data.addAll(o);

        notifyDataSetChanged();

    }


    private void addFakeAds() {
        UnifiedNativeAd unifiedNativeAd = new UnifiedNativeAd() {
            @Override
            public String getHeadline() {
                return null;
            }

            @Override
            public List<NativeAd.Image> getImages() {
                return null;
            }

            @Override
            public String getBody() {
                return null;
            }

            @Override
            public NativeAd.Image getIcon() {
                return null;
            }

            @Override
            public String getCallToAction() {
                return null;
            }

            @Override
            public String getAdvertiser() {
                return null;
            }

            @Override
            public Double getStarRating() {
                return null;
            }

            @Override
            public String getStore() {
                return null;
            }

            @Override
            public String getPrice() {
                return null;
            }

            @Override
            public VideoController getVideoController() {
                return null;
            }

            @Override
            public NativeAd.AdChoicesInfo getAdChoicesInfo() {
                return null;
            }

            @Override
            public String getMediationAdapterClassName() {
                return null;
            }

            @Override
            public boolean isCustomMuteThisAdEnabled() {
                return false;
            }

            @Override
            public List<MuteThisAdReason> getMuteThisAdReasons() {
                return null;
            }

            @Override
            public void muteThisAd(MuteThisAdReason muteThisAdReason) {

            }

            @Override
            public void setMuteThisAdListener(MuteThisAdListener muteThisAdListener) {

            }

            @Override
            public Bundle getExtras() {
                return null;
            }

            @Override
            public void destroy() {

            }

            @Override
            public void setUnconfirmedClickListener(UnconfirmedClickListener unconfirmedClickListener) {

            }

            @Override
            public void cancelUnconfirmedClick() {

            }

            @Override
            public void enableCustomClickGesture() {

            }

            @Override
            public boolean isCustomClickGestureEnabled() {
                return false;
            }

            @Override
            public void recordCustomClickGesture() {

            }

            @Override
            public void performClick(Bundle bundle) {

            }

            @Override
            public boolean recordImpression(Bundle bundle) {
                return false;
            }

            @Override
            public void reportTouchEvent(Bundle bundle) {

            }

            @Override
            public MediaContent getMediaContent() {
                return null;
            }

            @Override
            protected Object zzjq() {
                return null;
            }

            @Override
            public Object zzjv() {
                return null;
            }

            @Nullable
            @Override
            public ResponseInfo getResponseInfo() {
                return null;
            }

            @Override
            public void setOnPaidEventListener(@Nullable OnPaidEventListener onPaidEventListener) {

            }
        };
        setAds(unifiedNativeAd);
    }

    public void setAds(UnifiedNativeAd ads) {
        this.ads.add(ads);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) instanceof UnifiedNativeAd ? TYPE_AD : TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AD) {
            View view = LayoutInflater.from(this.context).inflate(R.layout.card_medium_ads, parent, false);
            return new AdTemplateViewHolder(view);
        }
        View view = LayoutInflater.from(this.context).inflate(R.layout.card_news_rv, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewtype = getItemViewType(position);
        if (viewtype == TYPE_AD) {
            AdTemplateViewHolder vh = (AdTemplateViewHolder) holder;
            AdsManager adsManager = new AdsManager(context);
            adsManager.createUnifiedAds(5, R.string.admob_nativ_ads_id1, new AdUnifiedListening() {
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    Log.e("LoadAdError", loadAdError.getMessage());
                }

                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    count++;
                    vh.setUnifiedNativeAd((unifiedNativeAd));
                    //Toast.makeText(SecondActivity.this, getAdLoader().isLoading()+" Succ "+v, Toast.LENGTH_SHORT).show();
                }
            });


            return;
        }
        NewsModel menuItem = (NewsModel) data.get(position);
        NewsViewHolder vh = (NewsViewHolder) holder;
        NewsViewHolder menuItemHolder = (NewsViewHolder) holder;
        if (menuItem != null) {
            vh.maintitle.setText(menuItem.getMaintitle());
            vh.secondarytitle.setText(menuItem.getSecondarytitle());

            Picasso.get().load(menuItem.getPosterurl()).into(vh.poster);
            (menuItemHolder).time.setText(menuItem.getTime());
            menuItemHolder.card.setOnClickListener(view -> {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("id", menuItem.getId());
                intent.putExtra("title", menuItem.getMaintitle());
                intent.putExtra("imageposter", menuItem.getPosterurl());
                intent.putExtra("html", menuItem.getDescription());
                intent.putExtra("HOST", HOST);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView maintitle, secondarytitle, time;
        ImageView poster;
        CardView card;

        NewsViewHolder(View v) {
            super(v);
            maintitle = v.findViewById(R.id.mainTitle);
            secondarytitle = v.findViewById(R.id.secondarytitle);
            time = v.findViewById(R.id.time);
            poster = v.findViewById(R.id.poster);
            card = v.findViewById(R.id.card);
        }
    }
}
