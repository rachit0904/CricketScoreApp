package com.cricketexchange.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.News.newsFrag;
import com.cricketexchange.project.ui.home.homeFrag;
import com.cricketexchange.project.ui.more.moreFrag;
import com.cricketexchange.project.ui.schedule.scheduleFrag;
import com.cricketexchange.project.ui.series.seriesFrag;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout=findViewById(R.id.tabs);
        tabLayout.selectTab(tabLayout.getTabAt(2));
        addFragment(new homeFrag());
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setFragment(int position) {
        switch (position+1){
            case 1:{
                addFragment(new newsFrag());
                break;
            }case 2:{
                addFragment(new seriesFrag());
                break;
            }case 3:{
                addFragment(new homeFrag());
                break;
            }case 4:{
                addFragment(new scheduleFrag());
                break;
            }case 5:{
                addFragment(new moreFrag());
                break;
            }
        }
    }

    public void addFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();
    }

}