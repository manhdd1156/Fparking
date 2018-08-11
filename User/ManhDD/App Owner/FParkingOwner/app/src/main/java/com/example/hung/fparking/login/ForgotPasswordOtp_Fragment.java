package com.example.hung.fparking.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

public class ForgotPasswordOtp_Fragment extends AppCompatActivity implements OnClickListener, IAsyncTaskHandler {
    private static View view;
    private static EditText otp;
    private static EditText newPassword;
    private static EditText reNewPassword;
    private static Button confirm;
    private static TextView error;
    Button btnOK;
    private static ImageView back;
    public String phone;
    public static String APP_TAG = "AccountKit";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgotpassotp);
        // ánh xạ
//        otp = (EditText) view.findViewById(R.id.otp);
        confirm = (Button) findViewById(R.id.confirmBtn);
        newPassword = (EditText) findViewById(R.id.password);
        reNewPassword = (EditText) findViewById(R.id.confirmPassword);
        back = (ImageView) findViewById(R.id.back);

        setListeners();
    }

    public String getPhoneConfirm() {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();
                System.out.println("ID: " + accountKitId);

                boolean SMSLoginMode = false;

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    phone = phoneNumber.toString();
                }
            }

            @Override
            public void onError(final AccountKitError error) {
                System.out.println("Error: " + error.toString());
            }
        });
        return phone;
    }

    // Set Listeners over buttons
    private void setListeners() {
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Replace SignUp Fragment on Back Presses
            case R.id.back:
//                new MainActivity().replaceForgotPasswordFragment();
                finish();
                break;

            case R.id.confirmBtn:

                System.out.println("newpass : " + newPassword.getText());
                System.out.println("renewpass : " + reNewPassword.getText());
                System.out.println(newPassword.getText().equals(reNewPassword.getText()));
                if(newPassword.getText().toString().isEmpty() || newPassword.getText().toString().equals("") || reNewPassword.getText().toString().isEmpty() || reNewPassword.getText().toString().equals("")) {
                    showDialog("Hãy nhập mật khẩu cần đổi");
                }
                else if(newPassword.getText().toString().length()<6 || newPassword.getText().toString().length()>24) {
                    showDialog("mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự");
                }else if(!newPassword.getText().toString().equals(reNewPassword.getText().toString())) {
                    showDialog("Mật khẩu mới không giống nhau, vui lòng nhập lại");
                }
                if (newPassword.getText().toString().equals(reNewPassword.getText().toString())) {
                    new ManagerLoginTask("forgotpassword", getPhoneConfirm(), newPassword.getText().toString(), new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            showDialog("Đổi mật khẩu thành công");
                        }
                    });

                }
                // Call Submit button task
                break;

        }
    }

    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ForgotPasswordOtp_Fragment.this);
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
                finish();
//                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intentLogin);
            }
        });
    }

    @Override
    public void onPostExecute(Object o) {

    }
}
