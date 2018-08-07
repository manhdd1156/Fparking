package com.example.hung.fparking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileActivity extends AppCompatActivity implements IAsyncTaskHandler {
    EditText name;
    EditText phone;
    EditText address;
    EditText password;
    EditText changePass;
    TextView tvError;
    TextView tvSuccess;
    Button btnConfirm;
    AlertDialog dialog;
    ImageView backProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button mUpdate = (Button) findViewById(R.id.btnUpdate);
        name = (EditText) findViewById(R.id.tbName);
        phone = (EditText) findViewById(R.id.tbPhone);
        address = (EditText) findViewById(R.id.tbAddress);
        tvSuccess = (TextView) findViewById(R.id.tvSuccess);
        changePass = findViewById(R.id.tbPass);
        changePass.setFocusable(false);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePass = new Intent(ProfileActivity.this, ChangePassword.class);
                startActivity(intentChangePass);
            }
        });
        backProfile = findViewById(R.id.imageViewBackProfile);
        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.homeActivity.recreate();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        setText();
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                tvSuccess.setVisibility(View.INVISIBLE);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
                btnConfirm = (Button) mView.findViewById(R.id.btnConfirm);
                btnConfirm.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkValidation();
                            }
                        });
                tvError = (TextView) mView.findViewById(R.id.tvError);
                password = (EditText) mView.findViewById(R.id.tbPassword);
            }

        });
    }

    public void setText() {
        name.setText("");
        phone.setText("");
        address.setText("");
        name.setHint(Session.currentStaff.getName());
        phone.setHint(Session.currentStaff.getPhone());
        address.setHint(Session.currentStaff.getAddress());
    }

    public void checkValidation() {
        try {
            String passMD5 = getMD5Hex(password.getText().toString());
            if (passMD5.equals(Session.currentStaff.getPass())) {
                if (name.getText().toString().isEmpty() || name.getText().toString().equals("")) {
                    Session.currentStaff.setName(name.getHint().toString());
                } else {
                    Session.currentStaff.setName(name.getText().toString());
                }
                if (address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                    Session.currentStaff.setAddress(address.getHint().toString());
                } else {
                    Session.currentStaff.setAddress(address.getText().toString());
                }
                if (phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                    Session.currentStaff.setPhone(phone.getHint().toString());
                } else {
                    Session.currentStaff.setPhone(phone.getText().toString());
                }
                new ManagerLoginTask("updateProfile", "", "", ProfileActivity.this);
            } else {
                tvError.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            System.out.println("Lỗi ProfileActivity/checkValidation : " + e);
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onPostExecute(Object o) {
        setText();
        tvSuccess.setText("Cập nhật thông tin thành công");
        tvSuccess.setTextColor(android.R.color.holo_green_light);
        tvSuccess.setVisibility(View.VISIBLE);
        dialog.cancel();
    }
}