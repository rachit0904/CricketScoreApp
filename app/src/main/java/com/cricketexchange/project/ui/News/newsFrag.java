package com.cricketexchange.project.ui.News;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.NewsRecyclerAdapter;
import com.cricketexchange.project.Models.NewsModel;
import com.cricketexchange.project.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.List;


public class newsFrag extends Fragment {

    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 5;
    public static final int ADS_PER_POST = 4;

    // The AdLoader used to load ads.
    private AdLoader adLoader;

    // List of MenuItems and native ads that populate the RecyclerView.
    private List<Object> mRecyclerViewItems = new ArrayList<>();

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        //init the ads
        MobileAds.initialize(getActivity(), initializationStatus -> {
        });


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // Specify an adapter.
        adapter = new NewsRecyclerAdapter(getActivity(), mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);
        MobileAds.initialize(getActivity(), initializationStatus -> {
        });
        loadNativeAds();
        addMenuItemsFromJson();
        return view;
    }

    int size = 0;

    private void addMenuItemsFromJson() {
        size = 60 + NUMBER_OF_ADS;

        for (int i = 0; i < size; i++) {
//            if (i != 0 && i % ADS_PER_POST == 0) {
//                mRecyclerViewItems.add(null);
//            } else {
//
//            }
            NewsModel newsModel = new NewsModel("" + i, this.getString(R.string.sample_title), this.getString(R.string.sample_title), "2 hours ago", "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png");
            mRecyclerViewItems.add(newsModel);


        }
        adapter.notifyDataSetChanged();

    }

    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(getActivity(), getString(R.string.admob_nativ_ads_id1));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }


    private void insertAdsInMenuItems() {
        if (mNativeAds.size() <= 0) {
            return;
        }

        int offset = 5;
        int index = 0;
        int indx = 0;
        for (int i = 0; i < mRecyclerViewItems.size(); i++) {
            if (indx > mNativeAds.size() - 1) {
                indx = 0;
            }
            if (index > mRecyclerViewItems.size()) {
                break;
            }
            if (index != 0) {
                UnifiedNativeAd ad = mNativeAds.get(indx);
                mRecyclerViewItems.add(index, ad);
            }

            index = index + offset;

        }
        adapter.notifyDataSetChanged();
    }

    // public List<Object> getRecyclerViewItems() {
    //     return mRecyclerViewItems;
    //  }

}
