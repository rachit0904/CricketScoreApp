package com.cricketexchange.project.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.cricketexchange.project.Constants.Constants;
import com.cricketexchange.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static com.cricketexchange.project.Constants.Constants.HOSTSUFFIX;
import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;

public class SplashScreen extends AppCompatActivity {


    private static final String TAG = "com.cricketexchange.project.Activity.SplashScreen";
    LottieAnimationView animationView;
    DatabaseReference mDatabase;
    Intent intent;
    private final int RC_APP_UPDATE = 999;
    SharedPreferences.Editor myEdit;
    private final int inAppUpdateType = AppUpdateType.IMMEDIATE;
    SharedPreferences sharedPreferences, sharedPreferences2;
    //admob
    String appid = "ca-app-pub-3940256099942544~3347511713";
    //inapp update
    private AppUpdateManager mAppUpdateManager;
    private com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateAppid();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("prefixData", MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences("prefixData", MODE_APPEND);
        myEdit = sharedPreferences.edit();
        animationView = findViewById(R.id.loadingAnim);
        intent = new Intent(this, MainActivity.class);
        mDatabase = FirebaseDatabase.getInstance().getReference("Hosts");
        mDatabase.child("HOST").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    intent.putExtra("HOST", Constants.HOST + sharedPreferences2.getString("hostIP", "") + HOSTSUFFIX);
//                    Toast.makeText(SplashScreen.this, "Something Wents Wrong", Toast.LENGTH_SHORT).show();
                } else {
                    myEdit.putString("hostIp", (task.getResult()).getValue().toString());
                    myEdit.apply();
                    if (sharedPreferences2.getString("hostIP", "").isEmpty() ||
                            !sharedPreferences2.getString("hostIP", "").equalsIgnoreCase((task.getResult()).getValue().toString())) {
                        myEdit.putString("hostIp", (task.getResult()).getValue().toString());
                        myEdit.commit();
                        intent.putExtra("HOST", Constants.HOST + sharedPreferences2.getString("hostIP", "") + HOSTSUFFIX);
                    } else {
                        intent.putExtra("HOST", Constants.HOST + sharedPreferences2.getString("hostIP", "") + HOSTSUFFIX);
                    }
                }
            }
        });
        //in app update
        update();
        updateAdsId();


        mDatabase = FirebaseDatabase.getInstance().getReference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        },4500);

    }


    private void refresh() {
        boolean connection = isNetworkAvailable();
        if (!connection) {
            startActivity(new Intent(SplashScreen.this, NetworkFailureActivity.class));

        }else{
            startActivity(new Intent(SplashScreen.this, MainActivity.class));

            finish();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;

    }

    private void updateAdsId() {
        SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adsid");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (int i = 0; i < snapshot.getChildrenCount(); i++) {
//                    Toast.makeText(SplashScreen.this, "Size:" + snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                    String name = snapshot.child(String.valueOf(i)).child("name").getValue().toString();
                    String value = snapshot.child(String.valueOf(i)).child("value").getValue().toString();
                    Log.d(name, value);
                    if (name.equals("appid")) {
//                        Toast.makeText(SplashScreen.this, name + ":" + value + "", Toast.LENGTH_SHORT).show();

                    }
                    myEdit.putString(name, value);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        myEdit.apply();
    }

    @SuppressLint("LongLogTag")
    private void updateAppid() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("Admob", MODE_PRIVATE);
            String Appid = sharedPreferences.getString("appid", "ca-app-pub-3940256099942544~3347511713");
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
            Log.d(TAG, "Name Found: " + myApiKey);
            ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", Appid);//"ca-app-pub-3940256099942544~3347511713");//you can replace your key APPLICATION_ID here
            String ApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
            Log.d(TAG, "ReNamed Found: " + ApiKey);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
    }

    void update() {
        // Creates instance of the manager.
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        // Returns an intent object that you use to check for an update.
        appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();
        //lambda operation used for below listener
        //For flexible update
        installStateUpdatedListener = installState -> {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            }
        };
        mAppUpdateManager.registerListener(installStateUpdatedListener);
        inAppUpdate();
    }

    @Override
    protected void onDestroy() {
        mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        try {
            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() ==
                        UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // If an in-app update is already running, resume the update.
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                inAppUpdateType,
                                this,
                                RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            });


            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
                //For flexible update
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            //when user clicks update button
            if (resultCode == RESULT_OK) {
                Toast.makeText(SplashScreen.this, "App download starts...", Toast.LENGTH_LONG).show();
            } else if (resultCode != RESULT_CANCELED) {
                //if you want to request the update again just call checkUpdate()
                Toast.makeText(SplashScreen.this, "App download canceled.", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_IN_APP_UPDATE_FAILED) {
                Toast.makeText(SplashScreen.this, "App download failed.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void inAppUpdate() {

        try {
            // Checks that the platform will allow the specified type of update.
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            // For a flexible update, use AppUpdateType.FLEXIBLE
                            && appUpdateInfo.isUpdateTypeAllowed(inAppUpdateType)) {
                        // Request the update.

                        try {
                            mAppUpdateManager.startUpdateFlowForResult(
                                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                    appUpdateInfo,
                                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                    inAppUpdateType,
                                    // The current activity making the update request.
                                    SplashScreen.this,
                                    // Include a request code to later monitor this update request.
                                    RC_APP_UPDATE);
                        } catch (IntentSender.SendIntentException ignored) {

                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void popupSnackbarForCompleteUpdate() {
        try {
            Snackbar snackbar =
                    Snackbar.make(
                            findViewById(R.id.rootlayout),
                            "An update has just been downloaded.\nRestart to update",
                            Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("INSTALL", view -> {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.completeUpdate();
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.live));
            snackbar.show();

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}