package com.cricketexchange.project.ui.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.MoreAdapter;
import com.cricketexchange.project.Models.MoreModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;


public class moreFrag extends Fragment {
    RecyclerView icc, settings, visit, support, about;
    ArrayList<MoreModel> icc_list = new ArrayList<>(), settings_list = new ArrayList<>(), visit_list = new ArrayList<>(), support_list = new ArrayList<>(), about_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.more_fragment, container, false);
        MoreAdapter icc_adapter = new MoreAdapter(icc_list, getActivity());
        MoreAdapter settings_adapter = new MoreAdapter(settings_list, getActivity());
        MoreAdapter visit_adapter = new MoreAdapter(visit_list, getActivity());
        MoreAdapter support_adapter = new MoreAdapter(support_list, getActivity());
        MoreAdapter about_adapter = new MoreAdapter(about_list, getActivity());
        icc = v.findViewById(R.id.iccranking_lv);
        icc.setAdapter(icc_adapter);
        settings = v.findViewById(R.id.setting_lv);
        settings.setAdapter(settings_adapter);
        visit = v.findViewById(R.id.visit_lv);
        visit.setAdapter(visit_adapter);
        support = v.findViewById(R.id.support_lv);
        support.setAdapter(support_adapter);
        about = v.findViewById(R.id.about_lv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        icc.setHasFixedSize(true);
        icc.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        settings.setHasFixedSize(true);
        settings.setLayoutManager(layoutManager1);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        visit.setHasFixedSize(true);
        visit.setLayoutManager(layoutManager2);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext());
        support.setHasFixedSize(true);
        support.setLayoutManager(layoutManager3);

        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getContext());
        about.setHasFixedSize(true);
        about.setLayoutManager(layoutManager4);


        about.setAdapter(about_adapter);
        MoreModel moreModel = new MoreModel(R.drawable.person_female_25, "ICC Women's Ranking", R.drawable.ic_outline_navigate_next_24);
        icc_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.person_25, "ICC Men's Ranking", R.drawable.ic_outline_navigate_next_24);
        icc_list.add(moreModel);

        moreModel = new MoreModel(R.drawable.translation_25, "App Language", R.drawable.ic_outline_navigate_next_24);
        settings_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.alarm_25, "Notification Setting", R.drawable.ic_outline_navigate_next_24);
        settings_list.add(moreModel);

        moreModel = new MoreModel(R.drawable.facebook_25, "Facebook", R.drawable.ic_outline_navigate_next_24);
        visit_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.twitter_25, "Twitter", R.drawable.ic_outline_navigate_next_24);
        visit_list.add(moreModel);

        moreModel = new MoreModel(R.drawable.star_filled_25, "Rate Us", R.drawable.ic_outline_navigate_next_24);
        support_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.downloading_updates_25, "Check For Updates", R.drawable.ic_outline_navigate_next_24);
        support_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.box_important_25, "Report Bug", R.drawable.ic_outline_navigate_next_24);
        support_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.share_25, "Share with Friends", R.drawable.ic_outline_navigate_next_24);
        support_list.add(moreModel);


        moreModel = new MoreModel(R.drawable.transparent_bg, "About Us", R.drawable.ic_outline_navigate_next_24);
        about_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.transparent_bg, "Terms And Condition", R.drawable.ic_outline_navigate_next_24);
        about_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.transparent_bg, "Privacy Polices", R.drawable.ic_outline_navigate_next_24);
        about_list.add(moreModel);
        moreModel = new MoreModel(R.drawable.transparent_bg, "Contact us for branding", R.drawable.ic_outline_navigate_next_24);
        about_list.add(moreModel);


        icc_adapter.notifyDataSetChanged();
        settings_adapter.notifyDataSetChanged();
        visit_adapter.notifyDataSetChanged();
        support_adapter.notifyDataSetChanged();
        about_adapter.notifyDataSetChanged();


        return v;
    }
}