package com.cricketexchange.project.Ads;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.R;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;


public class AdTemplateViewHolder extends RecyclerView.ViewHolder
{
    final TemplateView templateView;
    public AdTemplateViewHolder(@NonNull View itemView)
    {
        super(itemView);
        templateView = itemView.findViewById(R.id.ad_template_sm);
        NativeTemplateStyle style = new NativeTemplateStyle.Builder()
                .withMainBackgroundColor(new ColorDrawable(Color.parseColor("#ffffff"))).build();
        templateView.setStyles(style);
    }
    public void setUnifiedNativeAd(UnifiedNativeAd ads)
    {
        templateView.setNativeAd(ads);
    }
}
