package com.example.hung.fparkingowner.login;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class ForgotPassOtp_Activity extends AppCompatActivity implements OnClickListener, IAsyncTaskHandler {
    private static View view;
    private static EditText otp;
    private static EditText newPassword;
    private static EditText reNewPassword;
    private static EditText temp;

    private static Button confirm;
    private static TextView error;
    Button btnOK;
    private static ImageView back;
    public static String phone;
    public static String APP_TAG = "AccountKit";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgotpassotp);
        // ánh xạ
//        otp = (EditText) view.findViewById(R.id.otp);

        confirm = (Button) findViewById(R.id.confirmBtn);
        newPassword = (EditText) findViewById(R.id.password);
        reNewPassword = (EditText) findViewById(R.id.confirmPassword);
        temp = (EditText) findViewById(R.id.editTextTemp);
        back = (ImageView) findViewById(R.id.back);
phone ="";
getPhoneConfirm();

        setListeners();
    }

    public void getPhoneConfirm() {
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
                    System.out.println(phoneNumber.toString());
                    temp.setText(phoneNumber.toString().replace("+84","0"));
                    phone =phoneNumber.toString();

                }
            }

            @Override
            public void onError(final AccountKitError error) {
                System.out.println("Error: " + error.toString());
            }
        });

    }

    // Set Listeners over buttons
    private void setListeners() {
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                // Replace SignUp Fragment on Back Presses
                case R.id.back:
//                new MainActivity().replaceForgotPasswordFragment();
                    finish();
                    break;

                case R.id.confirmBtn:

                    System.out.println("newpass : " + newPassword.getText());
                    System.out.println("renewpass : " + reNewPassword.getText());
                    System.out.println(temp.getText().toString());
                    if (newPassword.getText().toString().isEmpty() || newPassword.getText().toString().equals("") || reNewPassword.getText().toString().isEmpty() || reNewPassword.getText().toString().equals("")) {
                        showDialog("Hãy nhập mật khẩu cần đổi", 0);
                    } else if (newPassword.getText().toString().length() < 6 || newPassword.getText().toString().length() > 24 ||
                            reNewPassword.getText().toString().length() < 6 || reNewPassword.getText().toString().length() > 24) {
                        showDialog("mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự", 0);
                    } else if (!newPassword.getText().toString().equals(reNewPassword.getText().toString())) {
                        showDialog("Mật khẩu mới không giống nhau, vui lòng nhập lại", 0);
                    } else if (newPassword.getText().toString().equals(reNewPassword.getText().toString())) {
                        System.out.println("phone = " + phone);
                        new ManagerLoginTask("forgotpassword", temp.getText().toString(), getMD5Hex(newPassword.getText().toString()), new IAsyncTaskHandler() {
                            @Override
                            public void onPostExecute(Object o) {
                                showDialog("Đổi mật khẩu thành công", 1);
                            }
                        });

                    }
                    // Call Submit button task
                    break;

            }
        }catch (Exception e) {

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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ForgotPassOtp_Activity.this);
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
                if(type==1)
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
