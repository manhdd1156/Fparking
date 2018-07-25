package com.example.hung.fparking.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hung.fparking.DialogActivity;
import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.change_space.NumberPickerActivity;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.notification.Notification;

import static com.example.hung.fparking.config.Constants.PICK_CONTACT_REQUEST;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private static int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Session.spref = getSharedPreferences("intro", 0);
        fragmentManager = getSupportFragmentManager();
//        if (savedInstanceState == null) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (Session.spref.getBoolean("first_time", false)) {
//                        fragmentManager
//                                .beginTransaction()
//                                .replace(R.id.frameContainer, new Login_Fragment(),
//                                        Constants.Login_Fragment).commit();
//                    } else {
//
//                        new ManagerLoginTask("second_time", "", "", new IAsyncTaskHandler() {
//                            @Override
//                            public void onPostExecute(Object o) {
//                                Intent myIntent = new Intent(MainActivity.this, Notification.class);
//                                MainActivity.this.startService(myIntent);
//                            }
//                        });
////
//                        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
//                        startActivity(homeIntent);
//                        finish();
//                    }
//                }
//            }, SPLASH_TIME_OUT);


            // If savedinstnacestate is null then replace login fragment

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();

        Intent dialog = new Intent(MainActivity.this, DialogActivity.class);
                        startActivity(dialog);
//        Intent intent = new Intent(MainActivity.this, NumberPickerActivity.class);
//        startActivityForResult(intent,PICK_CONTACT_REQUEST);

    }

    // Replace Login Fragment with animation
    protected void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(),
                        Utils.Login_Fragment).commit();
    }

    // Replace SignUp Fragment with animation
    protected void replaceSignUpFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new SignUp_Fragment(),
                        Utils.SignUp_Fragment).commit();
    }

    // Replace Forgot Password Fragment with animation
    protected void replaceForgotPasswordFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new ForgotPassword_Fragment(),
                        Utils.ForgotPassword_Fragment).commit();
    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment);

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
