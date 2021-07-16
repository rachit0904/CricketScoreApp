package com.cricketexchange.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.admin.SystemUpdateInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static com.cricketexchange.project.Constants.Constants.HOSTNAME;
import static com.cricketexchange.project.Constants.Constants.HOSTSUFFIX;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView animationView;
    DatabaseReference mDatabase;
    Intent intent;
    SharedPreferences sharedPreferences,sharedPreferences2;
    SharedPreferences.Editor myEdit;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("prefixData",MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences("prefixData",MODE_APPEND);
        myEdit = sharedPreferences.edit();
        animationView = findViewById(R.id.loadingAnim);
        intent = new Intent(this, MainActivity.class);
        mDatabase = FirebaseDatabase.getInstance().getReference("Hosts");
        mDatabase.child("HOST").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    intent.putExtra("HOST", Constants.HOST + sharedPreferences2.getString("hostIP","") + HOSTSUFFIX);
//                    Toast.makeText(SplashScreen.this, "Something Wents Wrong", Toast.LENGTH_SHORT).show();
                } else {
                    myEdit.putString("hostIp", (task.getResult()).getValue().toString());
                    myEdit.commit();
                    if(sharedPreferences2.getString("hostIP","").isEmpty() ||
                            !sharedPreferences2.getString("hostIP","").equalsIgnoreCase((task.getResult()).getValue().toString())) {
                        myEdit.putString("hostIp", (task.getResult()).getValue().toString());
                        myEdit.commit();
                        intent.putExtra("HOST", Constants.HOST + sharedPreferences2.getString("hostIP","") + HOSTSUFFIX);
                    }else{
                        intent.putExtra("HOST", Constants.HOST + sharedPreferences2.getString("hostIP","") + HOSTSUFFIX);
                    }
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 5500);

    }


    private void refresh() {
        boolean connection = isNetworkAvailable();
        if (!connection) {
            startActivity(new Intent(SplashScreen.this, NetworkFailureActivity.class));
        } else {
            startActivity(intent);
            finish();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

}