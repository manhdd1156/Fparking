package ise1005.edu.fpt.vn.myrestaurant.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import ise1005.edu.fpt.vn.myrestaurant.config.Session;

/**
 * Created by sengx on 10/25/2017.
 */

public class CheckSession {

    Activity activity;

    public CheckSession(Activity act){

        this.activity = act;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setTitle("Permission Denied!");

        alertDialogBuilder
                .setMessage("You don't have permission to see this")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Session.currentUser = null;
                        activity.finish();
                    }
                });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
