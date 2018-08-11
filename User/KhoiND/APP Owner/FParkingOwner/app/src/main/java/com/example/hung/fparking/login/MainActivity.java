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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.DriverDTO;
import com.example.hung.fparking.model.CheckNetwork;
import com.example.hung.fparking.notification.Notification;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements IAsyncTaskHandler{
    private static EditText phoneNumber, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;

    public static int APP_REQUEST_CODE = 3301;
    public static String APP_TAG = "AccountKit";
    private String event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        Session.homeActivity = this;
        Session.spref = getSharedPreferences("intro", 0);
        setProperties();
    }

    private void setProperties() {
        // ánh xạ
        phoneNumber = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginBtn);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signUp = (TextView) findViewById(R.id.createAccount);

        // Event
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp();
                event = "forgot";
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp();
                event = "register";
            }
        });
    }

    private void checkValidation() {
        // Get phone and password
//        phoneNumber.setText("01288028666");
//        password.setText("12345");
        String getPhone = phoneNumber.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Constants.regEx);

        Matcher m = p.matcher(getPhone);

        // Check for both field is empty or not
        if (getPhone.equals("") || getPhone.isEmpty()) {
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.login_layout),
                    "Hãy nhập số điện thoại");
        }
        if (getPassword.equals("") || getPassword.isEmpty()) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.login_layout),
                    "Hãy nhập mật khẩu");
        }
        // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.login_layout),
                    "Số điện thoại không đúng");
            // Else do login and do your stuff
        else {
            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setPhone(getPhone);
//            new DriverLoginTask("first_time", driverDTO, getPassword, MainActivity.this);
            Toast.makeText(getApplicationContext(), "Do Login.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void otp() {
        LoginType loginType = LoginType.PHONE;
        Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.TOKEN
                );

        configurationBuilder.setUIManager(new SkinManager(
                        SkinManager.Skin.CONTEMPORARY,
                        getResources().getColor(R.color.white_greyish),
                        R.drawable.car,
                        SkinManager.Tint.WHITE,
                        0.0
                )
        );

        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String responseMessage;
            if (loginResult.getError() != null) {
                responseMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                responseMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    responseMessage = "Success: " + loginResult.getAccessToken().getAccountId();
                } else {
                    responseMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                goToMyLoggedInActivity();
            }
            log(responseMessage);
        }
    }

    private void goToMyLoggedInActivity() {
        if (event.equals("forgot")) {
            Intent intentSignup = new Intent(getApplicationContext(), SignUp_Fragment.class);
            intentSignup.putExtra("action", "forgot");
            startActivity(intentSignup);
        } else if (event.equals("register")) {
            Intent intentSignup = new Intent(getApplicationContext(), SignUp_Fragment.class);
            intentSignup.putExtra("action", "register");
            startActivity(intentSignup);
        }

    }

    private void log(String msj) {
        Log.println(Log.DEBUG, APP_TAG, msj);
    }

    @Override
    public void onPostExecute(Object o) {
        if (Boolean.TRUE.equals(o)) {
//            Notification notification= new Notification();
            Intent intent = new Intent(MainActivity.this, Notification.class);
            System.out.println("đăng nhập thành công");
            startService(intent);
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        } else {
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.login_layout),
                    "Số điện thoại hoặc mật khẩu không đúng");
        }
    }
}
