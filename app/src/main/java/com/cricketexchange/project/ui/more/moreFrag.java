package com.cricketexchange.project.ui.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.cricketexchange.project.Activity.AboutActivity;
import com.cricketexchange.project.BuildConfig;
import com.cricketexchange.project.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class moreFrag extends Fragment implements View.OnClickListener {
    LinearLayout AppLanguage, Notification, fb, rate, update, report, share;
    CoordinatorLayout about, privacy, terms, contact;
    final static String FBPAGEURL = "https://facebook.com/";
    Intent myIntent;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.more_fragment, container, false);
        AppLanguage = v.findViewById(R.id.l1);
        Notification = v.findViewById(R.id.l2);
        fb = v.findViewById(R.id.l3);
        rate = v.findViewById(R.id.l5);
        update = v.findViewById(R.id.l6);
        report = v.findViewById(R.id.l7);
        share = v.findViewById(R.id.l8);
        about = v.findViewById(R.id.l19);
        privacy = v.findViewById(R.id.l10);
        terms = v.findViewById(R.id.l11);
        contact = v.findViewById(R.id.l12);

        AppLanguage.setOnClickListener(this);
        Notification.setOnClickListener(this);
        fb.setOnClickListener(this);
        rate.setOnClickListener(this);
        update.setOnClickListener(this);
        report.setOnClickListener(this);
        share.setOnClickListener(this);
        about.setOnClickListener(this);
        privacy.setOnClickListener(this);
        terms.setOnClickListener(this);
        contact.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l1: //AppLanguage
                changeLanguage();
                break;
            case R.id.l2: //Notification
                myIntent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(myIntent);
                break;
            case R.id.l3: //fb
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FBPAGEURL)));
                break;
            case R.id.l5: //rate
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                break;
            case R.id.l6: //update
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                break;
            case R.id.l7: //report
                Toast.makeText(getActivity(), "AppSettings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.l8: //share
                share();
                break;
            case R.id.l19: //about
                myIntent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(myIntent);
                break;
            case R.id.l10: //privacy
                myIntent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(myIntent);
                break;
            case R.id.l11: //terms
                myIntent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(myIntent);
                break;
            case R.id.l12: //contact
                myIntent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(myIntent);
                break;
            default:
                Toast.makeText(getActivity(), "Something Wents Wrong", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void changeLanguage() {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.more_bottom_sheet_dialog);

        ImageView close = bottomSheetDialog.findViewById(R.id.close);
        close.setOnClickListener(view -> bottomSheetDialog.cancel());
        bottomSheetDialog.show();

    }

    private void share() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getActivity().getString(R.string.app_name));
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share App"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}