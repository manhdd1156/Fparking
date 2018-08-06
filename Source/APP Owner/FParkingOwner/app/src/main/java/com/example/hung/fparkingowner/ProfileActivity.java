package com.example.hung.fparkingowner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerLoginTask;
import com.example.hung.fparkingowner.config.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileActivity extends AppCompatActivity implements IAsyncTaskHandler {
    EditText name;
    EditText phone;
    EditText address;
    EditText password;
    TextView tvError;
    TextView tvSuccess;
    Button btnConfirm;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button mUpdate = (Button) findViewById(R.id.btnUpdate);
        name = (EditText) findViewById(R.id.tbName);
        phone = (EditText) findViewById(R.id.tbPhone);
        address = (EditText) findViewById(R.id.tbAddress);
        tvSuccess = (TextView) findViewById(R.id.tvSuccess);
        setText();
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if ((name.getText().toString().isEmpty() || name.getText().toString().equals("")) &&
                        (address.getText().toString().isEmpty() || address.getText().toString().equals("")) &&
                (phone.getText().toString().isEmpty() || phone.getText().toString().equals(""))) {
                    tvSuccess.setText("Hãy nhập nội dung muốn cập nhật");
                    tvSuccess.setTextColor(android.R.color.holo_red_dark);
                    tvSuccess.setVisibility(View.VISIBLE);
                } else {
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
                    password = (EditText) mView.findViewById(R.id.tbPass);
                }


            }
        });
    }

    public void setText() {
        name.setText("");
        phone.setText("");
        address.setText("");
        name.setHint(Session.currentOwner.getName());
        phone.setHint(Session.currentOwner.getPhone());
        address.setHint(Session.currentOwner.getAddress());
    }

    public void checkValidation() {
        try {
            String passMD5 = getMD5Hex(password.getText().toString());
            if (passMD5.equals(Session.currentOwner.getPass())) {
                if (name.getText().toString().isEmpty() || name.getText().toString().equals("")) {
                    Session.currentOwner.setName(name.getHint().toString());
                } else {
                    Session.currentOwner.setName(name.getText().toString());
                }
                if (address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                    Session.currentOwner.setAddress(address.getHint().toString());
                } else {
                    Session.currentOwner.setAddress(address.getText().toString());
                }
                if (phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                    Session.currentOwner.setPhone(phone.getHint().toString());
                } else {
                    Session.currentOwner.setPhone(phone.getText().toString());
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
