package com.example.hung.fparking.model;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;

import com.example.hung.fparking.IntroApplication;

public class CheckNetwork {

    private final Activity mActivity;
    private final Context mContext;

    public CheckNetwork(Activity mActivity, Context mContext) {
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void createDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if (!isNetworkConnected()) {
                            createDialog();
                        } else {
                            Intent intent = new Intent(mContext, IntroApplication.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                        break;
                }
            }
        };
        builder.setTitle("RẤT TIẾC :-(")
                .setMessage("Kết nối mạng đã bị tắt. Vui lòng kết nối mạng và thử lại")
                .setPositiveButton("THỬ LẠI", dialogClickListener).setCancelable(false).show();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
