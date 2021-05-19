package com.cricketexchange.project.Ads;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public abstract class AdUnifiedListening extends AdListener implements UnifiedNativeAd.OnUnifiedNativeAdLoadedListener
{
    private AdLoader adLoader;

    public AdLoader getAdLoader()
    {
        return adLoader;
    }

    public void setAdLoader(AdLoader adLoader)
    {
        this.adLoader = adLoader;
    }
}
