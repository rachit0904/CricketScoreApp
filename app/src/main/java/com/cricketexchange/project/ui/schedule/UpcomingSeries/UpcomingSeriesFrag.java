package com.cricketexchange.project.ui.schedule.UpcomingSeries;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricketexchange.project.Adapter.Recyclerview.UpcomingSeriesAdapter;
import com.cricketexchange.project.Models.MatchesModel;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.SquadModel;
import com.cricketexchange.project.Models.UpcomingSeriesModel;
import com.cricketexchange.project.R;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UpcomingSeriesFrag extends Fragment {
    RecyclerView recyclerView;
    List<UpcomingSeriesModel> list=new ArrayList<>();
    List<SeriesModel> childList=new ArrayList<>();
    Set<String> dates=new TreeSet<>();
    List<SeriesModel> cList=new ArrayList<>();
    String arr[]={"Jan","Feb","March","April","May","June","July","August","Sept","Oct","Nov","Dec"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_upcoming_series, container, false);
        recyclerView=view.findViewById(R.id.upcomingSeriesRv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.clear();childList.clear();
        getData();
        setParentData();
        setChildData();
        UpcomingSeriesAdapter adapter=new UpcomingSeriesAdapter(getContext(),list,childList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void setChildData() {
        childList=cList;
    }

    private void setParentData() {
        UpcomingSeriesModel model=new UpcomingSeriesModel();
        for(String x:dates){
            model.setDate(x);
            list.add(model);
        }
    }

    private void getData() {
        for(int i=0;i<3;i++) {
            SeriesModel childModal = new SeriesModel();
            childModal.setSid("");
            childModal.setSeriesName("IPL 2021");
            childModal.setStatus("UPCOMING");
            String startdate = "7/9/2021"; //take start date here format [mm dd yyyy]
            String date[] = startdate.split("/");
            String enddate = "7/12/2021"; //take start date here format [mm dd yyyy]
            String date2[] = enddate.split("/");
            dates.add(arr[Integer.parseInt(date[0]) - 1] + " " + date[2]);
            childModal.setStartDate(arr[Integer.parseInt(date[0]) - 1] + " " + date[2]);
            //[start date to end date]
            String sD=(date[1]+" "+arr[Integer.parseInt(date[0]) - 1]),eD=(date2[1]+" "+arr[Integer.parseInt(date2[0]) - 1] );
            childModal.setDuration( sD+ "  to  " +eD );
            cList.add(childModal);
        }
    }

}
