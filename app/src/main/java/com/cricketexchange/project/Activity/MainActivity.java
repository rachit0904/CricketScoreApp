package com.cricketexchange.project.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.Models.CustomAdsModel;
import com.cricketexchange.project.R;
import com.cricketexchange.project.ui.News.newsFrag;
import com.cricketexchange.project.ui.home.homeFrag;
import com.cricketexchange.project.ui.more.moreFrag;
import com.cricketexchange.project.ui.schedule.schdeule;
import com.cricketexchange.project.ui.series.seriesFrag;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    RelativeLayout mAdView;
    ImageView customads;
    String HOST;
    boolean iscustomads = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        customads = findViewById(R.id.customAdview);
        HOST = getIntent().getStringExtra("HOST");
        Log.e("onCreate: ", HOST);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());

        SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
        String s1 = sharedPreferences.getString("ban1", "ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adView);
        mAdView.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        AdView mAdview = new AdView(this);
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) mAdView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mAdview.setLayoutParams(layoutParams);
        mAdview.setAdSize(AdSize.BANNER);
        mAdview.setAdUnitId(s1);
        mAdView.addView(mAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.selectTab(tabLayout.getTabAt(2));
        addFragment(new homeFrag());
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        loadcustomimage();
        ///switch for custom ads

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customads").child("iscustomasds");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String dataSnapshot = snapshot.getValue().toString();
                if (dataSnapshot.equalsIgnoreCase("false")) {
                    iscustomads = false;
                } else {
                    iscustomads = true;
                }
                change();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        loadinterstitialads();
        //ads settings
    }

    InterstitialAd mInterstitialAd;

    private void loadinterstitialads() {
        Random rand = new Random();
        prepareAd();
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> runOnUiThread(() -> {

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();

            } else {
                prepareAd();
                Log.d("TAG", " Interstitial not loaded");
            }

        }), rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, rand.nextInt(Constants.ADENDRANGE) + Constants.ADSTARTRANGE, TimeUnit.SECONDS);
    }

    public void prepareAd() {

        SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
        String i1 = sharedPreferences.getString("in2", "ca-app-pub-3940256099942544%2F1033173712");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(i1);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    private void setFragment(int position) {
        switch (position + 1) {
            case 1: {
                addFragment(new newsFrag());
                break;
            }
            case 2: {
                addFragment(new seriesFrag());
                break;
            }
            case 3: {
                addFragment(new homeFrag());
                break;
            }
            case 4: {
                addFragment(new schdeule());
                break;
            }
            case 5: {
                addFragment(new moreFrag());
                break;
            }
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }


    void change() {
        if (iscustomads) {
            mAdView.setVisibility(View.VISIBLE);
            customads.setVisibility(View.GONE);
            iscustomads = false;
        } else {
            mAdView.setVisibility(View.GONE);
            customads.setVisibility(View.VISIBLE);
            iscustomads = true;
        }


    }

    void loadcustomimage() {


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child("ads");
        listRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        StorageReference itm = item;

                        itm.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                                    Links.add(new SlideModel(uri.toString(), ScaleTypes.FIT));
//                                    adsloader.setImageList(Links);
//                                    Log.i("adsurl",uri.toString());


                                itm.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                    @Override
                                    public void onSuccess(StorageMetadata storageMetadata) {

                                        CustomAdsModel model = new CustomAdsModel();
                                        model.setUrl(uri.toString());
                                        String iname = storageMetadata.getName();
                                        String cname = iname.split("\\.")[0];
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Mobile").child(cname);
                                        databaseReference.addValueEventListener(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                String mobile = Objects.requireNonNull(snapshot.getValue()).toString();
                                                model.setNumber(mobile);
                                                Picasso.get().load(model.getUrl()).fit().into(customads);
                                                customads.setOnClickListener(v -> {
                                                    sendmessage(mobile);

                                                });

                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                            }
                                        });

                                    }
                                });


                            }
                        });

                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("adsurl", e.toString());
                    }
                });

    }

    private void sendmessage(String number) {
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode("Hi", "UTF-8");
            Intent whatappIntent = new Intent(Intent.ACTION_VIEW);
            whatappIntent.setPackage("com.whatsapp");
            whatappIntent.setData(Uri.parse(url));
            whatappIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (whatappIntent.resolveActivity(packageManager) != null) {
                startActivity(whatappIntent);
            } else {
                Toast.makeText(this, "WhatsApp Not installed", Toast.LENGTH_SHORT).show();
//                Log.d("msg", "WhatsApp Not installed");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
