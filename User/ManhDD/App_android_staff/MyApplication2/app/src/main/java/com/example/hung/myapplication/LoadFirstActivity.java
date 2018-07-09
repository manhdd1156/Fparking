package com.example.hung.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.hung.myapplication.config.Constants;
import com.example.hung.myapplication.login.Login_Fragment;
import com.example.hung.myapplication.login.MainActivity;
import com.example.hung.myapplication.login.Utils;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;

public class LoadFirstActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    Pusher pusher;
    Channel channel;
    private static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_first);
        getSupportActionBar().hide();
        final SharedPreferences spref =
                PreferenceManager.getDefaultSharedPreferences(this);
        fragmentManager = getSupportFragmentManager();

        // If savedinstnacestate is null then replace login fragment
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
                    Intent homeIntent = new Intent(LoadFirstActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}
