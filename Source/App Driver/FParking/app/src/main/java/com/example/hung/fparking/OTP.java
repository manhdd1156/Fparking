package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hung.fparking.login.SignUp_Fragment;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;

public class OTP extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 3301;
    public static String APP_TAG = "AccountKit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        LoginType loginType = LoginType.PHONE;
        Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.TOKEN
                );

        configurationBuilder.setUIManager(new SkinManager(
                        SkinManager.Skin.CONTEMPORARY,
                        getResources().getColor(R.color.colorAccent),
                        R.drawable.car,
                        SkinManager.Tint.BLACK,
                        0.10
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
        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getStringExtra("action");
            Intent intentSignup = new Intent(getApplicationContext(), SignUp_Fragment.class);
            intentSignup.putExtra("action", action);
            startActivity(intentSignup);
            finish();

        }
    }

    private void log(String msj) {
        Log.println(Log.DEBUG, APP_TAG, msj);
    }
}
