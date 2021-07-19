package com.cricketexchange.project.ui.matchdetail.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.cricketexchange.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class MatchInfo extends Fragment  {
    TextView tossResult, seriesName, matchType, startDateTime, venue, pt1SName, pt1FName, pt2SName, pt2FName, primaryUmpire, thirdUmpire, refree;
    CardView seriesCard;
    ImageView t1Logo, t2Logo;
    RelativeLayout t1Layout, t2Layout;
    ProgressBar progressBar;
    View view;
    String HOST;
    String ATseriesName = "NA", ATmatchType = "NA", ATvenueName = "NA", ATumpires = "NA", ATtumpire = "NA", ATrefree = "NA", ATtossMessage = "NA", ATstartdate = "NA",
            t1name = "NA", t1Sname = "NA", t1logourl = "NA", t1bg = "NA", t1color = "NA", t2name = "NA", t2Sname = "NA", t2logourl = "NA", t2bg = "NA", t2color = "NA", t1id = "NA", t2id = "NA";

    String sid, mid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_match_info, container, false);
        initialize();
        //setData();
        progressBar = view.findViewById(R.id.progressBar);
        sid = requireActivity().getIntent().getStringExtra("sid");
        mid = requireActivity().getIntent().getStringExtra("mid");
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        loadMatchInfo(sid,mid);
    }

    private void loadMatchInfo(String sid, String mid) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("MatchesHighlight").child(sid+"S"+mid).child("jsondata").child("matchDetail");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String tossMsg;
                try {
                    tossMsg = snapshot.child("tossMessage").getValue().toString();
                    String sName = snapshot.child("matchSummary").child("series").child("name").getValue().toString();
                    String seriesType = snapshot.child("matchSummary").child("cmsMatchAssociatedType").getValue().toString();
                    String startTime = snapshot.child("matchSummary").child("localStartDate").getValue().toString() + "," + snapshot.child("matchSummary").child("localStartTime").getValue().toString();
                    ;
                    ;
                    String venue = snapshot.child("matchSummary").child("venue").child("name").getValue().toString();
                    ;
                    ;
                    String t1FullName = snapshot.child("matchSummary").child("homeTeam").child("name").getValue().toString(),
                            t1ShortName = snapshot.child("matchSummary").child("homeTeam").child("shortName").getValue().toString(),
                            t1logoUrl = snapshot.child("matchSummary").child("homeTeam").child("logoUrl").getValue().toString();
                    String t2FullName = snapshot.child("matchSummary").child("awayTeam").child("name").getValue().toString(),
                            t2ShortName = snapshot.child("matchSummary").child("awayTeam").child("shortName").getValue().toString(),
                            t2logoUrl = snapshot.child("matchSummary").child("awayTeam").child("logoUrl").getValue().toString();

                    String u1 = snapshot.child("umpires").child("firstUmpire").child("name").getValue().toString(),
                            u2 = snapshot.child("umpires").child("secondUmpire").child("name").getValue().toString(),
                            u3 = snapshot.child("umpires").child("thirdUmpire").child("name").getValue().toString(),
                            ref = snapshot.child("umpires").child("referee").child("name").getValue().toString();
                    ATseriesName = sName;ATmatchType = seriesType;ATvenueName = venue;ATumpires = u1 + "," + u2;ATtumpire = u3;ATrefree = ref;ATtossMessage = tossMsg;
                    ATstartdate = startTime;t1name = t1FullName;t1Sname = t1ShortName;t1logourl = t1logoUrl;t1bg = "NA";t1color = "NA";t2name = t2FullName;
                    t2Sname = t2ShortName;t2logourl = t2logoUrl;t2bg = "NA";t2color = "NA";t1id = "NA";t2id = "NA";
                }catch (Exception e){

                }
                update();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void initialize() {
        tossResult = view.findViewById(R.id.tossStatus);
        seriesName = view.findViewById(R.id.tounamentName);
        seriesCard = view.findViewById(R.id.showSeriesDetail);
        matchType = view.findViewById(R.id.seriesType);
        venue = view.findViewById(R.id.venue);
        startDateTime = view.findViewById(R.id.startTime);
        pt1SName = view.findViewById(R.id.t1shortName);
        pt1FName = view.findViewById(R.id.t1fullName);
        t1Layout = view.findViewById(R.id.teamcard);
        t1Logo = view.findViewById(R.id.pt1Logo);
        pt2SName = view.findViewById(R.id.t2shortName);
        pt2FName = view.findViewById(R.id.t2fullName);
        t2Layout = view.findViewById(R.id.teamcard2);
        t2Logo = view.findViewById(R.id.pt2Logo);
        primaryUmpire = view.findViewById(R.id.umpires);
        thirdUmpire = view.findViewById(R.id.thirdUmpire);
        refree = view.findViewById(R.id.refree);
    }

    private void update() {
        progressBar.setVisibility(View.GONE);

        if (t1logourl != null) {
            if (t1logourl.trim().length() != 0) {
                Picasso.get().load(t1logourl).into(t1Logo);
            }
        }
        if (t1logourl != null) {
            if (t2logourl.trim().length() != 0) {
                Picasso.get().load(t2logourl).into(t2Logo);
            }
        }


        if (ATmatchType.trim().length() != 0) {
            matchType.setText(ATmatchType);
        }
        if (ATseriesName.trim().length() != 0) {
            seriesName.setText(ATseriesName);
        }
        if (ATtossMessage.trim().length() != 0) {
            tossResult.setText(ATtossMessage);
        }
        if (ATstartdate.trim().length() != 0) {
            startDateTime.setText(ATstartdate);
        }
        if (t1Sname.trim().length() != 0) {
            pt1SName.setText(t1Sname);
        }
        if (t1name.trim().length() != 0) {
            pt1FName.setText(t1name);
        }

        if (t2Sname.trim().length() != 0) {
            pt2SName.setText(t2Sname);
        }
        if (t2name.trim().length() != 0) {
            pt2FName.setText(t2name);
        }


        if (ATtumpire.trim().length() != 0) {
            thirdUmpire.setText(ATtumpire);
        }
        if (ATumpires.trim().length() != 0) {
            primaryUmpire.setText(ATumpires);
        }
        if (ATvenueName.trim().length() != 0) {
            venue.setText(ATvenueName);
        }

        if (ATrefree.trim().length() != 0) {
            refree.setText(ATrefree);
        }


    }

}