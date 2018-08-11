package com.example.hung.fparking.login;

import android.app.AlertDialog;
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

import com.example.hung.fparking.ChangePassword;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.DriverLoginTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.DriverDTO;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Fragment extends AppCompatActivity implements IAsyncTaskHandler {

    TextView phone, already_user, textViewAlert, signup_password_title;
    EditText password, confirmPassword;
    Button signUpBtn, btnOK;
    AlertDialog dialog;

    public static String APP_TAG = "AccountKit";
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        setUserInformation();
        setProperties();
    }

    private void setProperties() {
        //tạo dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignUp_Fragment.this);
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        textViewAlert = mView.findViewById(R.id.textViewAlert);
        btnOK = mView.findViewById(R.id.btnOK);


        //Ánh xạ
        phone = findViewById(R.id.phoneDK);
        password = findViewById(R.id.passwordDK);
        confirmPassword = findViewById(R.id.confirmPasswordDK);
        signUpBtn = findViewById(R.id.signUpBtn);
        already_user = findViewById(R.id.already_user);

        signUpBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(new Intent(SignUp_Fragment.this, MainActivity.class));
                finish();
            }
        });

        // set ui
        signup_password_title = findViewById(R.id.signup_password_title);
        if (getIntent() != null) {
            if (action.equals("forgot")) {
                signup_password_title.setText("Quên mật khẩu");
                signUpBtn.setText("Đổi mật khẩu");
            } else if (action.equals("register")) {
                signup_password_title.setText("Đăng ký");
            }
        }
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
        // Get phone and password
        String newPass = password.getText().toString();
        String cfPassword = confirmPassword.getText().toString();

        // Check for both field is empty or not
        if (newPass.isEmpty() || cfPassword.isEmpty()) {
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.signup_password_layout),
                    "Hãy nhập đủ thông tin");
        }
        // Check lengh of pass
        else if (newPass.length() < 6 || newPass.length() > 24) {
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.signup_password_layout),
                    "Mật khẩu mới phải từ 6 đến 24 ký tự");
        }
        // Check if old pass and new pass not match
        else if (!newPass.equals(cfPassword))
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.signup_password_layout),
                    "Mật khẩu mới và xác nhận mật khẩu không trùng nhau");
            // Else do login and do your stuff
        else {

            if (getIntent() != null) {
                if (action.equals("forgot")) {
                    DriverDTO driverDTO = new DriverDTO();
                    driverDTO.setPhone(phone.getText().toString());
                    new DriverLoginTask("phone", driverDTO, password.getText().toString(), SignUp_Fragment.this);
                } else if (action.equals("register")) {
                    DriverDTO driverDTO = new DriverDTO();
                    driverDTO.setPhone(phone.getText().toString());
                    new DriverLoginTask("create", driverDTO, password.getText().toString(), SignUp_Fragment.this);
                }
            }
        }
    }

    @Override
    public void onPostExecute(Object o, String action) {
        if (Boolean.TRUE.equals(o)) {
            if (action.equals("create")) {
                textViewAlert.setText("Tạo tài khoản thành công!");
                dialog.show();
            } else if (action.equals("phone")) {
                textViewAlert.setText("Mật khẩu đã được đổi thành công!");
                dialog.show();
            }
        } else {
            if (action.equals("create")) {
                textViewAlert.setText("Tài khoản này đã tồn tại!");
                dialog.show();
            } else if (action.equals("phone")) {
                textViewAlert.setText("Đổi mật khẩu không thành công!");
                dialog.show();
            }
        }
    }
}

