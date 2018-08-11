package com.example.hung.fparking.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;

public class LoginActivity extends AppCompatActivity implements OnClickListener, IAsyncTaskHandler {
    private static View view;

    private static EditText phoneNumber, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp,error;
    //    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private FragmentManager fragmentManager;
    Button btnOK;
    private String event;
    public static int APP_REQUEST_CODE = 3301;

    public static String APP_TAG = "AccountKit";

//    private StaffLoginTask mStaffLoginTask = null;
    private SharedPreferences spref;
    private SharedPreferences.Editor editor;
//    private String getPhone;
//    private String getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        Session.spref = getSharedPreferences("intro", 0);
        Session.homeActivity = LoginActivity.this;
        // ánh xạ
        phoneNumber = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginBtn);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signUp = (TextView) findViewById(R.id.createAccount);

        setListeners();
    }


    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();

                break;

            case R.id.forgot_password:
                otp();
                event = "forgot";

                break;
            case R.id.createAccount:
                otp();
                event = "register";

                break;
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
            System.out.println(responseMessage);
//            log(responseMessage);
        }
    }
    private void goToMyLoggedInActivity() {
        if (event.equals("forgot")) {

            Intent intentSignup = new Intent(getApplicationContext(), ForgotPasswordOtp_Fragment.class);
            intentSignup.putExtra("action", "forgot");
            startActivity(intentSignup);
        } else if(event.equals("register")){
            Intent intentSignup = new Intent(getApplicationContext(), SignUp_Fragment.class);
            intentSignup.putExtra("action", "register");
            startActivity(intentSignup);
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
            loginLayout.startAnimation(shakeAnimation);
            showDialog("Hãy nhập số điện thoại");
        }
        if (getPassword.equals("") || getPassword.isEmpty()) {
            loginLayout.startAnimation(shakeAnimation);
            showDialog("Hãy nhập mật khẩu");
        }
        // Check if email id is valid or not
        else if (!m.find())
            showDialog("Số điện thoại hoặc mật khẩu không đúng");
        else {
            new ManagerLoginTask("first_time",getPhone,getPassword, this);
//            mStaffLoginTask = new StaffLoginTask(getPhone, getPassword, this);
//            mStaffLoginTask.execute((Void) null);
//            Toast.makeText(getActivity(), "Vui lòng đợi xíu.", Toast.LENGTH_SHORT)
//                    .show();
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
        if (Boolean.TRUE.equals(o)) {
            System.out.println("đăng nhập thành công");
            startActivity( new Intent(this, HomeActivity.class));
            finish();
        } else {
            showDialog("Số điện thoại hoặc không đúng");
        }
    }
}

