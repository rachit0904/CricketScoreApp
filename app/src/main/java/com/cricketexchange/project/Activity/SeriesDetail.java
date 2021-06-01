package com.cricketexchange.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cricketexchange.project.Pager.SeriesViewPager;
import com.cricketexchange.project.R;
import com.google.android.material.tabs.TabLayout;

public class SeriesDetail extends AppCompatActivity implements View.OnClickListener {
    TextView seriesTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);
        ImageButton bck = findViewById(R.id.bckbutton);
        bck.setOnClickListener(this);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.pager);
        TextView textView = findViewById(R.id.inboxTitle);
        textView.setText(getIntent().getStringExtra("name"));
        SeriesViewPager adapter = new SeriesViewPager(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(adapter);

        ;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bckbutton) {
            finish();
        }
    }
}