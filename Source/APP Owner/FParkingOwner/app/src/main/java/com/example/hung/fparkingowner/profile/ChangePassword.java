package com.example.hung.fparkingowner.profile;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparkingowner.R;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerLoginTask;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePassword extends AppCompatActivity implements IAsyncTaskHandler{
    ImageView backChangePass;
    Button btnUpdate,btnOK;
    EditText tbOldPass,tbNewPass,tbConfirmPass;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        tbOldPass = (EditText) findViewById(R.id.tbOldPass);
        tbNewPass = (EditText) findViewById(R.id.tbNewPass);
        tbConfirmPass = (EditText) findViewById(R.id.tbConfirmNewPass);
        btnUpdate = (Button) findViewById(R.id.btnChangePass);

        backChangePass = findViewById(R.id.imageViewBackChangePass);
        backChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(tbOldPass.getText().toString().isEmpty() || tbOldPass.getText().toString().equals("") ||
                            tbNewPass.getText().toString().isEmpty() || tbNewPass.getText().toString().equals("") ||
                            tbConfirmPass.getText().toString().isEmpty() || tbConfirmPass.getText().toString().equals("")) {  // when is Empty
                        showDialog("Hãy nhập mật khẩu",0);
                    }
                    else if(!getMD5Hex(tbOldPass.getText().toString()).equals(Session.currentOwner.getPass())) { // when wrong password
                        showDialog("mật khẩu cũ không đúng, vui lòng nhập lại",0);
                    }else if(tbNewPass.getText().toString().length()<6 || tbNewPass.getText().toString().length()>24) { // when length < 6 %% > 24
                        showDialog("mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự",0);
                    }
                    else if(!tbNewPass.getText().toString().equals(tbConfirmPass.getText().toString())) { // when confirm pass is wrong
                        showDialog("Xác nhận mật khẩu mới không giống, vui lòng nhập lại",0);
                    }else if(tbNewPass.getText().toString().equals(tbOldPass.getText().toString())) { // when new pass is old pass
                        showDialog("Mật khẩu mới bị trùng với mật khẩu cũ",0);
                    }
                    else {
                        Session.currentOwner.setPass(getMD5Hex(tbNewPass.getText().toString()));
                        new ManagerLoginTask("updateProfile", "", "", ChangePassword.this);
                        showDialog("Đổi mật khẩu thành công",1);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }
    public void showDialog(String text, final int type) {
        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(this);
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
                if(type==1) {
                    finish();
                }
            }
        });
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

    @Override
    public void onPostExecute(Object o) {

    }
}
