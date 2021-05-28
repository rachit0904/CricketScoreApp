package com.cricketexchange.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricketexchange.project.Adapter.Recyclerview.PlayerDataAdapter;
import com.cricketexchange.project.Models.PlayersDataModel;
import com.cricketexchange.project.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class TeamPlayersInfo extends AppCompatActivity implements View.OnClickListener {
    ImageView bck,teamLogo;
    TextView teamShortName,teamFullName;
    RecyclerView recyclerView;
    Toolbar materialToolbar;
    Boolean notifyFlag=false;
    List<PlayersDataModel> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_players_info);
        materialToolbar=findViewById(R.id.toolbar4);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setTitle("");
        bck=findViewById(R.id.back);
        teamLogo=findViewById(R.id.pt1Logo);
        teamShortName=findViewById(R.id.teamShortName);
        teamFullName=findViewById(R.id.teamFullName);
        bck.setOnClickListener(this);
        recyclerView=findViewById(R.id.players);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=getData();
        PlayerDataAdapter adapter=new PlayerDataAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private List<PlayersDataModel> getData() {
        //TODO set team image
        teamShortName.setText("RCB");
        teamFullName.setText("Royal Challengers Bangalore");
        teamShortName.setTextColor(getColor(R.color.live));
        List<PlayersDataModel> dataModelList = new ArrayList<>();
        {
            PlayersDataModel dataModel = new PlayersDataModel("Virat Kohli", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel);
            PlayersDataModel dataModel2 = new PlayersDataModel("Chris Gayle", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel2);
            PlayersDataModel dataModel3 = new PlayersDataModel("Yuzendra Chahal", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel3);
            PlayersDataModel dataModel4 = new PlayersDataModel("AB de Villers", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel4);
            PlayersDataModel dataModel5 = new PlayersDataModel("Devdutt Padikal", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel5);
            PlayersDataModel dataModel6 = new PlayersDataModel("Glenn Maxwel", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel6);
            PlayersDataModel dataModel7 = new PlayersDataModel("Washington Sunder", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel7);
            PlayersDataModel dataModel8 = new PlayersDataModel("Mohammad Siraj", "", "Batsman", "Right hand", "Right arm bowler");
            dataModelList.add(dataModel8);
        }
        return dataModelList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.notification){
            if (!notifyFlag)
            {
                //TOOD add team to db for verification and further process of sending notification
                item.setIcon(getResources().getDrawable(R.drawable.notifyon));
                Snackbar.make(findViewById(R.id.notification), "notification turned on for this team's all upcoming matches!", Snackbar.LENGTH_SHORT).show();
                notifyFlag = true;
                setPreference();
            }
            else {
                item.setIcon(getResources().getDrawable(R.drawable.notify));
                Snackbar.make(findViewById(R.id.notification), "notification turned off!", Snackbar.LENGTH_SHORT).show();
                notifyFlag = false;
                setPreference();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDefaults(){
        // TODO set notification on or off on bases of whether teamId set for notification [from db]
    }

    public void teamMatchNotification(Context context, String title, String text) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("demo2","team match Update", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(text);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"demo2");
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.ic_baseline_sports_cricket_24);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setStyle(bigText);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(context);
        managerCompat.notify(1,builder.build());

    }

    private void setPreference() {
        SharedPreferences preferences=getSharedPreferences("prefs",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("team notification", notifyFlag);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        if(v==bck){
            finish();
        }
    }
}
