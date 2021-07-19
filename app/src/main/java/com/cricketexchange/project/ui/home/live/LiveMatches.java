package com.cricketexchange.project.ui.home.live;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesChildAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.schedule.schdeule;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class LiveMatches extends Fragment implements View.OnClickListener{
    private  String HOST = "";
    RecyclerView recyclerView;
    CardView card;
    final ArrayList<MatchesChildModel> childList = new ArrayList<>();
    ProgressBar progressBar;
    RelativeLayout noMatchLayout;
    ImageSlider adsloader;
    MatchesChildAdapter adapter;
    List<SlideModel> Links;
    final List<String> series =new ArrayList<>();
    public LiveMatches() {
        super(R.layout.fragment_live_matches);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        printmeta();
        card = view.findViewById(R.id.upcoming);
        card.setOnClickListener(this);
        Links = new ArrayList<>();
        noMatchLayout=view.findViewById(R.id.noMatchLayout);
        recyclerView = view.findViewById(R.id.liveMatches);
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        progressBar = view.findViewById(R.id.progressBar);
        adsloader = view.findViewById(R.id.ads_show);
        childList.clear();
        series.clear();
        load();
       ads_run();
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        loadAllSeries();
        loadSeriesMatches();
    }

    private void loadAllSeries() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Series");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(int i=0;i<snapshot.child("1").child("jsondata").child("seriesIdList").getChildrenCount();i++){
                    series.add(snapshot.child("1").child("jsondata").child("seriesIdList").child(String.valueOf(i)).getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void loadSeriesMatches() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Matches");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                childList.clear();
                for(String sid : series){
                    DataSnapshot dataSnapshot = snapshot.child(sid).child("jsondata").child("matchList").child("matches");
                    int i;
                    for ( i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        MatchesChildModel matchesChildModel = new MatchesChildModel();
                        matchesChildModel.setsId(sid);
                        String mid = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("id").getValue());
                        matchesChildModel.setmId(mid);
                        matchesChildModel.setPremiure(dataSnapshot.child(String.valueOf(i)).child("series").child("name").getValue().toString());//series name
                        matchesChildModel.setStatus(dataSnapshot.child(String.valueOf(i)).child("status").getValue().toString());//currentMatchState
                        matchesChildModel.setIsDraw(dataSnapshot.child(String.valueOf(i)).child("isMatchDrawn").getValue().toString());//status upcomming mandatory//currentMatchState
                        matchesChildModel.setTeam1(dataSnapshot.child(String.valueOf(i)).child("homeTeam").child("shortName").getValue().toString());
                        matchesChildModel.setTeam2(dataSnapshot.child(String.valueOf(i)).child("awayTeam").child("shortName").getValue().toString());
                        matchesChildModel.setIsmultiday(dataSnapshot.child(String.valueOf(i)).child("isMultiDay").getValue().toString());
                        matchesChildModel.setIswomen(dataSnapshot.child(String.valueOf(i)).child("isWomensMatch").getValue().toString());
                        try {
                            matchesChildModel.setType(dataSnapshot.child(String.valueOf(i)).child("cmsMatchType").getValue().toString());
                        }catch (Exception e){
                            matchesChildModel.setType("null");
                        }
                        String team1id = (dataSnapshot.child(String.valueOf(i)).child("homeTeam").child("id").getValue().toString());
                        String team2id = (dataSnapshot.child(String.valueOf(i)).child("awayTeam").child("id").getValue().toString());
//                        //in case of match
                        {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MatchesHighlight");
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    try {
                                        String winnigteamid = snapshot.child(sid + "S" + mid).child("jsondata").child("matchDetail").child("matchSummary").child("winningTeamId").getValue().toString();
                                        if (winnigteamid.equals(team1id)) {
                                            matchesChildModel.setWinTeamName(matchesChildModel.getTeam1());
                                        }
                                        else if (winnigteamid.equals(team2id)) {
                                            matchesChildModel.setWinTeamName(matchesChildModel.getTeam2());
                                        }else{
                                            matchesChildModel.setWinTeamName("N.A");
                                        }
                                    }catch (Exception e){

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }
                        try{
                            String logourl1 = dataSnapshot.child(String.valueOf(i)).child("homeTeam").child("logoUrl").getValue().toString();
                            String logourl2 = dataSnapshot.child(String.valueOf(i)).child("awayTeam").child("logoUrl").getValue().toString();
                            if(!logourl1.isEmpty() || !logourl2.isEmpty()) {
                                matchesChildModel.setTeam1Url(logourl1);
                                matchesChildModel.setTeam2Url(logourl2);
                            }else{
                                matchesChildModel.setTeam1Url("");
                                matchesChildModel.setTeam2Url("");
                            }
                        }catch (Exception e){
                            matchesChildModel.setTeam1Url("");
                            matchesChildModel.setTeam2Url("");
                        }
                        matchesChildModel.setMatchSummery(dataSnapshot.child(String.valueOf(i)).child("matchSummaryText").getValue().toString());
                        try {
                            matchesChildModel.setTeam1score(dataSnapshot.child(String.valueOf(i)).child("scores").child("homeScore").getValue().toString());
                            matchesChildModel.setTeam1over(dataSnapshot.child(String.valueOf(i)).child("scores").child("homeOvers").getValue().toString());
                            matchesChildModel.setTeam2score(dataSnapshot.child(String.valueOf(i)).child("scores").child("awayScore").getValue().toString());
                            matchesChildModel.setTeam2over(dataSnapshot.child(String.valueOf(i)).child("scores").child("awayOvers").getValue().toString());
                        }catch (Exception e){
                            matchesChildModel.setTeam1score("-");
                            matchesChildModel.setTeam1over("-");
                            matchesChildModel.setTeam2score("-");
                            matchesChildModel.setTeam2over("-");
                        }
                        String dateTime = dataSnapshot.child(String.valueOf(i)).child("startDateTime").getValue().toString();
                        String[] arr = dateTime.split("T");//arr[0] gives start date
                        String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
                        //add data to parent and child list
                        String date[] = arr[0].split("-");
                        String sD = (date[2] + "-" + date[1] + "-" + date[0]);
                        SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
                        Date d = new Date();
                        matchesChildModel.setStartDate(sD);
                        matchesChildModel.setStartTime(arr2[0].split(":")[0] + ":" + arr2[0].split(":")[1]);
                        if (matchesChildModel.getStatus().equalsIgnoreCase("LIVE") ||
                                matchesChildModel.getStatus().equalsIgnoreCase("INPROGRESS") &&
                                        matchesChildModel.getStartDate().equals(sobj.format(d))) {
                            childList.add(matchesChildModel);
                        }
                    }
                }
                update();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void ads_run(){
     FirebaseStorage storage = FirebaseStorage.getInstance();
     StorageReference listRef = storage.getReference().child("ads");
     Log.i("adsurl","Strated");
     listRef.listAll()
             .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                 @Override
                 public void onSuccess(ListResult listResult) {
                     for (StorageReference item : listResult.getItems()) {
                         item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 Links.add(new SlideModel(uri.toString(), ScaleTypes.FIT));
                                 adsloader.setImageList(Links);
                                 Log.i("adsurl",uri.toString());
                             }
                         });

                     }

                 }
             })
             .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Log.i("adsurl",e.toString());
                 }
             });

 }


    public void addFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == card) {
            TabLayout tabLayout = getActivity().findViewById(R.id.tabs);
            tabLayout.selectTab(tabLayout.getTabAt(3));
            addFragment(new schdeule());
        }
    }

    private void update() {
        progressBar.setVisibility(View.GONE);
        if (childList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noMatchLayout.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noMatchLayout.setVisibility(View.VISIBLE);
        }
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MatchesChildAdapter(getContext(), childList);
        adapter.setHOST(HOST);
        recyclerView.setAdapter(adapter);
    }

    void printmeta() {
//        ApplicationInfo ai = null;
//        try {
//            ai = requireActivity().getPackageManager().getApplicationInfo(requireActivity().getPackageName(), PackageManager.GET_META_DATA);
//            Bundle bundle = ai.metaData;
//            String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
//            Log.d(TAG, myApiKey);
//            Toast.makeText(requireContext(), "" + myApiKey, Toast.LENGTH_SHORT).show();
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

    }


}