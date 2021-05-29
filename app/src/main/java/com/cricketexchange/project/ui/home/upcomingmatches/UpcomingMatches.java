package com.cricketexchange.project.ui.home.upcomingmatches;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UpcomingMatches extends Fragment {
    RecyclerView recyclerView;
    List<MatchesModel> modelList=new ArrayList<>();
    List<MatchesChildModel> childModelList=new ArrayList<>();
    List<MatchesModel> parentList=new ArrayList<>();
    List<MatchesChildModel> childList=new ArrayList<>();
    Set<String> dates=new TreeSet<>();
    String months[]={"Jan","Feb","March","April","May","June","July","August","Sept","Oct","Nov","Dec"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        recyclerView=view.findViewById(R.id.upcomingMatches);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modelList.clear();childModelList.clear();
        setData();
        setParentData();
        setChildDate();
        MatchesAdapter adapter=new MatchesAdapter(getContext(),modelList,childModelList);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void setChildDate() {
        childModelList=childList;
    }

    private void setParentData() {
        MatchesModel model=new MatchesModel();
        for(String x:dates){
            model.setDate(x);
            modelList.add(model);
        }
    }

    private void setData(){
        for(int i=0;i<2;i++){
            MatchesChildModel matchesChildModel = new MatchesChildModel();
            //fetch all data into childModelList but for date
            matchesChildModel.setsId("");
            matchesChildModel.setmId("");
            matchesChildModel.setPremiure("IPL 2021");//series name
            matchesChildModel.setStatus("UPCOMING");//status upcomming mandatory
            matchesChildModel.setTeam1("RCB");
            matchesChildModel.setTeam2("DC");
            matchesChildModel.setTeam1Url("");
            matchesChildModel.setTeam2Url("");
            matchesChildModel.setT1iIsBatting("true");
            matchesChildModel.setT2IsBatting("false");
            matchesChildModel.setMatchSummery("Delhi capitals win by 7 wickets");
            //set date to match modellist and match childmodallist;
            String dateTime = "2021-06-10T10:00:00Z";
            String[] arr = dateTime.split("T");//arr[0] gives start date
            String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
            //add data to parent and child list
            String date[]=arr[0].split("-");
            String sD=(date[2]+" "+months[Integer.parseInt(date[1]) - 1]+","+date[0]);
            dates.add(sD);
            matchesChildModel.setStartDate(sD);
            matchesChildModel.setStartTime(arr2[0]);
            childList.add(matchesChildModel);
        }
    }

}