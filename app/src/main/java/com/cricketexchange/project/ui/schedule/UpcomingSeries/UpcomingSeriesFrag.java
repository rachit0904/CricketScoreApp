package com.cricketexchange.project.ui.schedule.UpcomingSeries;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketexchange.project.Adapter.Recyclerview.UpcomingSeriesAdapter;
import com.cricketexchange.project.Models.SeriesModel;
import com.cricketexchange.project.Models.UpcomingSeriesModel;
import com.cricketexchange.project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpcomingSeriesFrag extends Fragment {
    RecyclerView recyclerView;
    List<UpcomingSeriesModel> list = new ArrayList<>();
    List<SeriesModel> childList = new ArrayList<>();
    Set<Date> dates = new TreeSet<>();
    List<SeriesModel> cList = new ArrayList<>();
    SimpleDateFormat sobj = new SimpleDateFormat("MM/dd/yyyy");
    UpcomingSeriesAdapter adapter;
    ProgressBar progressBar;
    String arr[] = {"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nov", "Dec"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_series, container, false);
        recyclerView = view.findViewById(R.id.upcomingSeriesRv);
        recyclerView.hasFixedSize();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.clear();
        childList.clear();
        load();

        return view;
    }

    private void setChildData() {
        childList = cList;
    }

    private void setParentData() {

        for (Date x : dates) {
            UpcomingSeriesModel model = new UpcomingSeriesModel();
            model.setDate(sobj.format(x));
            list.add(model);
        }
    }

    private void getData() {
        for (int i = 0; i < 3; i++) {
            SeriesModel childModal = new SeriesModel();
            childModal.setSid("");
            childModal.setSeriesName("IPL 2021");
            childModal.setStatus("UPCOMING");
            String startdate = "7/9/2021"; //take start date here format [mm dd yyyy]
            String date[] = startdate.split("/");
            String enddate = "7/12/2021"; //take start date here format [mm dd yyyy]
            String date2[] = enddate.split("/");
            //  dates.add(arr[Integer.parseInt(date[0]) - 1] + " " + date[2]);
            //  childModal.setStartDate(arr[Integer.parseInt(date[0]) - 1] + " " + date[2]);
            //[start date to end date]
            String sD = (date[1] + " " + arr[Integer.parseInt(date[0]) - 1]), eD = (date2[1] + " " + arr[Integer.parseInt(date2[0]) - 1]);
            childModal.setDuration(sD + "  to  " + eD);
            cList.add(childModal);
        }
    }

    private void update(@NonNull Boolean isAt) {
        progressBar.setVisibility(View.GONE);

      //  Log.e("childList", String.valueOf(childList.size()));

        setParentData();


    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        new Load().execute("http://3.108.39.214/AllSeriesUpComing");
    }


    private class Load extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(@NonNull String... urls) {
            long totalSize = 0;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray data = object.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);


                        try {
                            SeriesModel SsriesModel = new SeriesModel();
                            //fetch all data into childModelList but for date
                            String id = obj.getString("id");
                            String status = obj.getString("status");
                            String startDateTime = obj.getString("startDateTime");
                            String type = obj.getString("type");
                            String name = obj.getString("name");
                            SsriesModel.setStatus(status);
                            SsriesModel.setSid(id);
                            SsriesModel.setSeriesName(name);
                            SsriesModel.setStartDate(startDateTime);
                            SsriesModel.setType(type);
                            ///SeriesModel.set


                            //set date to match modellist and match childmodallist;


                            String sD = startDateTime;
                            Date d = null;
                            try {
                                d = sobj.parse(sD);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dates.add(d);
                            childList.add(SsriesModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {
            update(true);
        }
    }


}
