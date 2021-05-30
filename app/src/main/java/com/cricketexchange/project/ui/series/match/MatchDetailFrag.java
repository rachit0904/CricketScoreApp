package com.cricketexchange.project.ui.series.match;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cricketexchange.project.Adapter.Recyclerview.MatchesAdapter;
import com.cricketexchange.project.Models.MatchesChildModel;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MatchDetailFrag extends Fragment {
    RecyclerView recyclerView;
    List<MatchesModel> modelList=new ArrayList<>();
    List<MatchesChildModel> childModelList=new ArrayList<>();
    List<MatchesModel> parentList=new ArrayList<>();
    List<MatchesChildModel> childList=new ArrayList<>();
    Set<Date> dates=new TreeSet<>();
    SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_match_detail, container, false);
        recyclerView=view.findViewById(R.id.matchDetailRv);
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
        for (Date x : dates) {
            MatchesModel model = new MatchesModel();
            model.setDate(sobj.format(x));
            modelList.add(model);
        }
    }

    private void setData(){
         for(int i=0;i<3;i++){
             MatchesChildModel matchesChildModel = new MatchesChildModel();
            //fetch all data into childModelList but for date
             matchesChildModel.setsId("");
             matchesChildModel.setmId("");
            matchesChildModel.setPremiure("IPL 2021");//series name
            matchesChildModel.setStatus("COMPLETED");
             matchesChildModel.setIsDraw("false");
             matchesChildModel.setWinTeamName("DC");
            matchesChildModel.setTeam1("RCB");
            matchesChildModel.setTeam2("DC");
            matchesChildModel.setTeam1Url("");
            matchesChildModel.setTeam2Url("");
            matchesChildModel.setT1iIsBatting("true");
            matchesChildModel.setT2IsBatting("false");
            if (matchesChildModel.getT1iIsBatting().equalsIgnoreCase("true")) {
                matchesChildModel.setTeam1score("136-4");
                matchesChildModel.setTeam1over("13.2");
            }else{
                matchesChildModel.setTeam1score("");
                matchesChildModel.setTeam1over("");
            }
            if (matchesChildModel.getT2IsBatting().equalsIgnoreCase("true")) {
                matchesChildModel.setTeam2score("136-4");
                matchesChildModel.setTeam2over("12.4");
            }else{
                matchesChildModel.setTeam2score("");
                matchesChildModel.setTeam2over("");
            }
            matchesChildModel.setMatchSummery("Delhi capitals win by 7 wickets");
            //set date to match modellist and match childmodallist;
            String dateTime = "2021-06-03T10:00:00Z";
            String[] arr = dateTime.split("T");//arr[0] gives start date
            String[] arr2 = arr[1].split("Z");//arr2[0] gives start time
            //add data to parent and child list
             String date[]=arr[0].split("-");
             String sD = (date[2] + "-" + date[1] + "-" + date[0]);
             SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");
             Date d= null;
             try {
                 d = sobj.parse(sD);
             } catch (ParseException e) {
                 e.printStackTrace();
             }
             dates.add(d);
             matchesChildModel.setStartDate(sD);
            matchesChildModel.setStartTime(arr2[0]);
            childList.add(matchesChildModel);
        }
    }

}