package com.example.hung.fparking.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.IntroApplication;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.notification.Notification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements OnClickListener, IAsyncTaskHandler {
    private static View view;

    private static EditText phoneNumber, password;
    private static Button loginButton,btnOK;
    private static TextView error;

    private SharedPreferences spref;
    private SharedPreferences.Editor editor;
//    private String getPhone;
//    private String getPassword;
    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Session.homeActivity = this;
        Session.spref = getSharedPreferences("intro", 0);
        editor = Session.spref.edit();
        phoneNumber = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;
        }
    }

    // Check Validation before login
    private void checkValidation() {
        // Get phone and password
        String getPhone = phoneNumber.getText().toString();
        String getPassword = password.getText().toString();
        if(getPhone.contains("+84")) {
            getPhone = getPhone.replace("+84","0");
        }
        // Check patter for email id
        Pattern p = Pattern.compile(Constants.regEx);

        Matcher m = p.matcher(getPhone);

        // Check for both field is empty or not
        if (getPhone.equals("") || getPhone.isEmpty()) {
//            loginLayout.startAnimation(shakeAnimation);
            showDialog("Hãy nhập số điện thoại");
        }
        else if (getPassword.equals("") || getPassword.isEmpty()) {
//            loginLayout.startAnimation(shakeAnimation);
            showDialog("Hãy nhập mật khẩu");
        }
        else if(getPassword.length()<6 || getPassword.length()>24) {
            showDialog("Mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự");
        }
        // Check if email id is valid or not
        else if (!m.find())
            showDialog("Số điện thoại hoặc mật khẩu không đúng");
            // Else do login and do your stuff
        else {
            new ManagerLoginTask("first_time", getPhone, getPassword, new IAsyncTaskHandler() {
                @Override
                public void onPostExecute(Object o) {
                    if (Boolean.TRUE.equals(o)) {
                        editor.putBoolean("first_time",false);
                        editor.commit();
                        System.out.println("đăng nhập thành công");
                        Intent myIntent = new Intent(LoginActivity.this, Notification.class);
                        LoginActivity.this.startService(myIntent);
                        startActivity( new Intent(LoginActivity.this, HomeActivity.class));
                        finish();

                    } else {
                        showDialog("Số điện thoại hoặc mật khẩu không đúng");
                    }
                }
            });
        }
    }
    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        error = (TextView) mView.findViewById(R.id.tvAlert);
        btnOK = (Button) mView.findViewById(R.id.btnOK);
        error.setText(text);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
    @Override
    public void onPostExecute(Object o) {

    }
}

