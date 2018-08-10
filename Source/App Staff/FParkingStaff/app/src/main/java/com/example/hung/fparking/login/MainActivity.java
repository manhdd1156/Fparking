package com.example.hung.fparking.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.model.CheckNetwork;
import com.example.hung.fparking.notification.Notification;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//        getSupportActionBar().hide();
        Session.homeActivity = this;
        Session.spref = getSharedPreferences("intro", 0);
        fragmentManager = getSupportFragmentManager();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        if (!Session.spref.getBoolean("first_time", false)) {
            setContentView(R.layout.activity_main);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Constants.Login_Fragment).commit();
        } else {
            try {
                new ManagerLoginTask("second_time", "", "", new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        setContentView(R.layout.activity_main);

                        if ((boolean) o) {
                            Intent myIntent = new Intent(MainActivity.this, Notification.class);
                            MainActivity.this.startService(myIntent);
                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                            finish();
                        } else if (!(boolean) o) {
                            System.out.println("=========================");
                            callLoginFrame();
                        }


                    }
                });
            } catch (Exception e) {

            }

        }


    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckNetwork checkNetwork = new CheckNetwork(MainActivity.this, getApplicationContext());
            if (!checkNetwork.isNetworkConnected()) {
                checkNetwork.createDialog();
            } else {
                System.out.println("đã có mạng");
//                recreate();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void callLoginFrame() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameContainer, new Login_Fragment(),
                        Constants.Login_Fragment).commit();
    }

    // Replace Login Fragment with animation
    protected void replaceLoginFragment() {
//        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(),
                        Constants.Login_Fragment).commit();
    }

    // Replace SignUp Fragment with animation
    protected void replaceSignUpFragment() {
//        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new SignUp_Fragment(),
                        Constants.SignUp_Fragment).commit();
    }

    // Replace Forgot Password Fragment with animation
    protected void replaceForgotPasswordFragment() {
//        fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new ForgotPassword_Fragment(),
                        Constants.ForgotPassword_Fragment).commit();
    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Constants.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Constants.ForgotPassword_Fragment);

        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task

        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else if (ForgotPassword_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }
}
