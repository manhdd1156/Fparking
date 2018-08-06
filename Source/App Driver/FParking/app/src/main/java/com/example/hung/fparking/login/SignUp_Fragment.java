package com.example.hung.fparking.login;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.DriverLoginTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.DriverDTO;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Fragment extends AppCompatActivity {

    TextView phone, already_user;
    EditText password, confirmPassword;
    Button signUpBtn;

    public static String APP_TAG = "AccountKit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        //Ánh xạ
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpBtn = findViewById(R.id.signUpBtn);
        already_user = findViewById(R.id.already_user);

        signUpBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent() != null) {
                    Intent intent = getIntent();
                    String action = intent.getStringExtra("action");
                    if (action.equals("forgot")) {

                    } else if (action.equals("register")) {
                        checkValidation();
                    }
                }
            }
        });
        setUserInformation();
    }

    public void setUserInformation() {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();
                log("ID: " + accountKitId);

                boolean SMSLoginMode = false;

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = "";
                if (phoneNumber != null) {
                    phoneNumberString = phoneNumber.toString();
                    phone.setText(phoneNumberString.replace("+84", "0"));
                }

                // Get email
                String email = account.getEmail();
                log("Email: " + email);

            }

            @Override
            public void onError(final AccountKitError error) {
                log("Error: " + error.toString());
            }
        });
    }

    private void log(String msj) {
        Log.println(Log.DEBUG, SignUp_Fragment.APP_TAG, msj);
    }

    // Check Validation before login
    private void checkValidation() {
        // Get password

        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        if (getPassword.equals("") || getPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Hãy nhập mật khẩu", Toast.LENGTH_SHORT)
                    .show();
        }

        if (!getPassword.equals(getConfirmPassword)) {
            Toast.makeText(getApplicationContext(), "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT)
                    .show();
        }
        // Check if email id is valid or not

        else {

            Toast.makeText(getApplicationContext(), "Do Login.", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}

