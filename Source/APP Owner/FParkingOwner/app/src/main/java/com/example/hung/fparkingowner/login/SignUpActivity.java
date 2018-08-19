package com.example.hung.fparkingowner.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparkingowner.R;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerLoginTask;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity implements IAsyncTaskHandler {
    TextView phone, already_user, textViewAlert, error;
    EditText password, confirmPassword;
    Button btnSignUp, btnOK;
    AlertDialog dialog;
    ImageView back;

    public static String APP_TAG = "AccountKit";
    String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);
        Intent intent = getIntent();
        action = intent.getStringExtra("action");

        setUserInformation();
        setProperties();

    }
    private void setProperties() {
        //tạo dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignUpActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.setCancelable(false);
        textViewAlert = mView.findViewById(R.id.tvAlert);
        btnOK = mView.findViewById(R.id.btnOK);


        //Ánh xạ
        phone = findViewById(R.id.phoneDK);
        password = findViewById(R.id.passwordDK);
        confirmPassword = findViewById(R.id.confirmPasswordDK);
        btnSignUp = findViewById(R.id.btnSignUp);
        already_user = findViewById(R.id.already_user);

        back = findViewById(R.id.back);

        already_user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            }
        });

        btnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (textViewAlert.getText().equals("Tạo tài khoản thành công!") || textViewAlert.getText().equals("Mật khẩu đã được đổi thành công!")) {
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }

            }
        });

//        // set ui
////        signup_password_title = findViewById(R.id.signup_password_title);
//        if (getIntent() != null) {
//            if (action.equals("register")) {
//                signup_password_title.setText("Đăng ký");
//            }
//        }
    }
    private void checkValidation() {
        // Get phone and password
        try {
            String newPass = password.getText().toString();
            String cfPassword = confirmPassword.getText().toString();

            // Check for both field is empty or not
            if (phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                showDialog("Hãy nhập số điện thoại", 0);
            } else if (newPass.isEmpty() || cfPassword.isEmpty()) {
                showDialog("Hãy nhập mật khẩu", 0);
            }
            // Check lengh of pass
            else if (newPass.length() < 6 || newPass.length() > 24) {
                showDialog("Mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự", 0);
            }
            // Check if old pass and new pass not match
            else if (!newPass.equals(cfPassword)) {
                showDialog("Xác nhận mật khẩu không chính xác", 0);

            }
            // Else do login and do your stuff
            else {
                String paramPhone = phone.getText().toString();
                if (phone.getText().toString().contains("+84")) {
                    paramPhone = paramPhone.replace("+84", "0");
                }
                new ManagerLoginTask("create", getMD5Hex(paramPhone), newPass, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        if ((boolean) o) {
                            showDialog("Tạo mới thành công!", 1);
                        } else {
                            showDialog("Số điện thoại đã tồn tại", 0);
                        }
                    }
                });
            }
        }catch (Exception e) {
            System.out.println("lỗi signup : " + e);
        }
    }
    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
    public void showDialog(String text, final int type) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignUpActivity.this);
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
                if (type == 1) {
                    recreate();
                }
            }
        });
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
        Log.println(Log.DEBUG, SignUpActivity.APP_TAG, msj);
    }


    @Override
    public void onPostExecute(Object o) {

    }
}

