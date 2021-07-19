package com.cricketexchange.project.ui.matchdetail.commentary;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cricketexchange.project.Adapter.Recyclerview.CommantaryAdapter;
import com.cricketexchange.project.Models.CommentaryModal;
import com.cricketexchange.project.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Commentary extends Fragment {
    RecyclerView commentryRv;
    final List<CommentaryModal> commentaries = new ArrayList<>();
    String mid, sid;
    CommantaryAdapter adapter;
    String HOST;
    ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_commentary, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        commentryRv = view.findViewById(R.id.commentary);
        sid = getActivity().getIntent().getStringExtra("sid");
        mid = getActivity().getIntent().getStringExtra("mid");
        HOST = requireActivity().getIntent().getStringExtra("HOST");
        //SetOverBallScore();
        commentaries.clear();
        //SetOverOverview();
        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        loadCommentary(sid,mid);
    }

    private void loadCommentary(String sid, String mid) {
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Commentary").child(sid + "S" + mid).child("jsondata")
                                                    .child("commentary").child("innings").child("0")
                                                    .child("overs").child("0").child("balls");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    commentaries.clear();
                    for(int i =Integer.parseInt(String.valueOf(snapshot.getChildrenCount()-1)); i>=0;i--) {
                        CommentaryModal modal = new CommentaryModal(
                                snapshot.child(String.valueOf(i)).child("comments").child("0").child("ballType").getValue().toString(),
                                snapshot.child(String.valueOf(i)).child("comments").child("0").child("text").getValue().toString());
                        commentaries.add(modal);
                    }
                    update();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Snackbar.make(view, "Match data NA", Snackbar.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Snackbar.make(view, "Match data NA", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void update() {
        progressBar.setVisibility(View.GONE);
        commentryRv.hasFixedSize();
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        commentryRv.setLayoutManager(manager);
        manager.setStackFromEnd(true);
        adapter = new CommantaryAdapter(getContext(), commentaries);
        commentryRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}