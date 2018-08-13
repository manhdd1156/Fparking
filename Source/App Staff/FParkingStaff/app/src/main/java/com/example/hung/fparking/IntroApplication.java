package com.example.hung.fparking;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.login.LoginActivity;
import com.example.hung.fparking.model.CheckNetwork;
import com.example.hung.fparking.notification.Notification;


public class IntroApplication extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;
    CheckNetwork checkNetwork;

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Session.homeActivity = this;
        Session.spref = getSharedPreferences("intro", 0);
//        mPreferencesEditor = Session.spref.edit();
//        mPreferencesEditor.clear().commit();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("first ?? " + Session.spref.getBoolean("first_time", false));
                if (!Session.spref.getBoolean("first_time", false)) {
                    new ManagerLoginTask("second_time", "", "", new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            if ((boolean) o) {   // nếu đăng nhập lần 2 sẽ chueyern luôn vào homeactivity
                                Intent myIntent = new Intent(IntroApplication.this, Notification.class);
                                IntroApplication.this.startService(myIntent);
                                Intent homeIntent = new Intent(IntroApplication.this, HomeActivity.class);
                                startActivity(homeIntent);
                                finish();
                            }
                            else {
                                Intent myIntent = new Intent(IntroApplication.this, LoginActivity.class);
                                startActivity(myIntent);
                                finish();
                            }
                        }
                    });
                } else {
                    Intent myIntent = new Intent(IntroApplication.this, LoginActivity.class);
                    startActivity(myIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }


}
