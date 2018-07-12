package com.example.hung.fparking;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.login.Login_Fragment;
import com.example.hung.fparking.login.MainActivity;
import com.example.hung.fparking.model.CheckNetwork;
import com.example.hung.fparking.model.GPSTracker;
import com.example.hung.fparking.notification.Notification;


public class Theme extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

//        getSupportActionBar().hide();

        // tạo SharedPreferences
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();

        final SharedPreferences spref =
                PreferenceManager.getDefaultSharedPreferences(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (spref.getString("phonenumber", "") == null && spref.getBoolean(
                        "first_time", false)) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.frameContainer, new Login_Fragment(),
                                    Constants.Login_Fragment).commit();
                } else if (!spref.getBoolean("first_time", false)) {
                    Intent homeIntent = new Intent(Theme.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

        // khởi tạo fragment
        fragmentManager = getSupportFragmentManager();

        // chay service noti/pusher
        Intent myIntent = new Intent(Theme.this, Notification.class);
        this.startService(myIntent);

        // kiểm tra có bật kết nối mạng không
        CheckNetwork checkNetwork = new CheckNetwork(Theme.this, getApplicationContext());
        if (!checkNetwork.isNetworkConnected()) {
            checkNetwork.createDialog();
        } else {
            splash();
        }

    }

    public void splash() {
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final String mPhoneNumber = tMgr.getLine1Number();

    }

}
