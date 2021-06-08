package com.cricketexchange.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.cricketexchange.project.R;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        animationView=findViewById(R.id.loadingAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        },5500);

    }


    private void refresh() {
        boolean connection=isNetworkAvailable();
        if(connection==false){
            startActivity(new Intent(SplashScreen.this, NetworkFailureActivity.class));
        }else{
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
        }
    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }

}