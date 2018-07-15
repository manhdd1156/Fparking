package com.example.hung.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.hung.myapplication.config.Constants;
import com.example.hung.myapplication.login.Login_Fragment;

public class IntroApplication extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_first);
        getSupportActionBar().hide();
        final SharedPreferences spref =
                PreferenceManager.getDefaultSharedPreferences(this);
        fragmentManager = getSupportFragmentManager();
        System.out.println("b1");
        // If savedinstnacestate is null then replace login fragment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                System.out.println("b2");
//                if (spref.getBoolean("first_time", false)) {
//                    System.out.println("b3");
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.testframe, new Login_Fragment(),
                                    Constants.Login_Fragment).commit();
//                    System.out.println("b4");
//                } else if (!spref.getBoolean("first_time", false)) {
//                    System.out.println("b32");
//                    SharedPreferences.Editor editor = spref.edit();
//                    editor.putBoolean("first_time",true);
//                    editor.commit();
//                    Intent homeIntent = new Intent(IntroApplication.this, Manage_Home.class);
//                    startActivity(homeIntent);
//                    System.out.println("b42");
//                    finish();
//                }
//                System.out.println("b5");
            }
        }, SPLASH_TIME_OUT);

    }
}
