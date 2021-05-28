package com.cricketexchange.project.ui.matchdetail.commentary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricketexchange.project.Adapter.Recyclerview.CommantaryAdapter;
import com.cricketexchange.project.Models.CommentaryModal;
import com.cricketexchange.project.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

public class Commentary extends Fragment {
    TextView ovrs,runs,wkts;
    Chip b1,b2,b3,b4,b5,b6;
    RecyclerView commentryRv;
    List<CommentaryModal> commentaries=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_commentary, container, false);
        ovrs=view.findViewById(R.id.over);
        runs=view.findViewById(R.id.runs);
        wkts=view.findViewById(R.id.wkts);
        commentryRv=view.findViewById(R.id.commentary);
        b1=view.findViewById(R.id.ball1);b2=view.findViewById(R.id.ball2);b3=view.findViewById(R.id.ball4);
        b4=view.findViewById(R.id.ball4);b5=view.findViewById(R.id.ball5);b6=view.findViewById(R.id.ball6);
        SetOverBallScore();
        SetOverOverview();
        commentryRv.hasFixedSize();
        commentryRv.setLayoutManager(new LinearLayoutManager(getContext()));
        commentaries=getData();
        CommantaryAdapter adapter=new CommantaryAdapter(getContext(),commentaries);
        commentryRv.setAdapter(adapter);
        return view;
    }

    private void SetOverOverview() {
        ovrs.setText("19.4");
        runs.setText("204");
        wkts.setText("7");
    }

    private int getBallColor(Boolean isWkt){
        return (isWkt) ? R.color.live : android.R.color.holo_green_dark ;
    }

    private void SetOverBallScore() {
        Boolean isWkt=false;
        int ball=Integer.parseInt("1"); //get current ball
        int color=getBallColor(isWkt); //check isWkt to get ball color
        AtomicReference<String> runs= new AtomicReference<>("2"); //get runs
        if(runs.get().equals("6") || runs.get().equals("4")){
            color= android.R.color.holo_blue_dark;
        }
        if ((isWkt)) {
            runs.set("W");
        }
        switch(ball){
            case 1:{
                b1.setText(runs.get());
                b1.setChipBackgroundColorResource(color);
                b2.setChipBackgroundColorResource(R.color.background);b3.setChipBackgroundColorResource(R.color.background);
                b4.setChipBackgroundColorResource(R.color.background);
                b5.setChipBackgroundColorResource(R.color.background);b6.setChipBackgroundColorResource(R.color.background);
                break;
            }case 2:{
                b2.setText(runs.get());
                b2.setChipBackgroundColorResource(color);
                break;
            }case 3:{
                b3.setText(runs.get());
                b3.setChipBackgroundColorResource(color);
                break;
            }case 4:{
                b4.setText(runs.get());
                b4.setChipBackgroundColorResource(color);
                break;
            }case 5:{
                b5.setText(runs.get());
                b5.setChipBackgroundColorResource(color);
                break;
            }case 6:{
                b6.setText(runs.get());
                b6.setChipBackgroundColorResource(color);
                break;
            }

        }
    }

    private List<CommentaryModal> getData() {
        List<CommentaryModal> list=new ArrayList<>();
        CommentaryModal modal=new CommentaryModal("Legit OffBat !","Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal);
        CommentaryModal modal2=new CommentaryModal("Legit OffBat !","Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal2);
        CommentaryModal modal3=new CommentaryModal("Legit OffBat !","Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal3);
        CommentaryModal modal4=new CommentaryModal("Legit OffBat !","Saqib Mahmood to Adam Lyth. Length ball, defending, Played to short leg for no runs");
        list.add(modal4);
        return list;
    }
}