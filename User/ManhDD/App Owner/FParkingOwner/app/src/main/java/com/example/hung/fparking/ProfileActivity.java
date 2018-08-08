package com.example.hung.fparking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileActivity extends AppCompatActivity implements IAsyncTaskHandler {
    ListView lv;
    TextView tvSpace, textViewMPhone;
    TextView tvAddress;
    NavigationView navigationView;
    View headerView;
    ImageView imageViewFParking;
    EditText tbPass;
    Button update;
    EditText name;
    EditText phone;
    EditText address;
    EditText password;
    EditText changePass;
    TextView tvError;
    TextView tvSuccess,error;
    Button btnConfirm,btnOK;
    AlertDialog dialog;
    ImageView backProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tbPass = findViewById(R.id.tbPassHP);
        tbPass.setFocusable(false);
        update = findViewById(R.id.btnUpdate);
        phone = (EditText) findViewById(R.id.tbPhone);
        name = (EditText) findViewById(R.id.tbName);
        address = (EditText) findViewById(R.id.tbAddress);
        tbPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePass = new Intent(ProfileActivity.this, ChangePassword.class);
                startActivity(intentChangePass);
            }
        });
        //Gọi alertDialog
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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
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
    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
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
                new ManagerLoginTask("updateProfile", "", "", new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        if((boolean)o) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int choice) {
                                    switch (choice) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                            try {

                                builder.setMessage("Số điện thoại đã tồn tại")
                                        .setPositiveButton("Yes", dialogClickListener).setCancelable(false).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } else {
                showDialog("Mật khẩu không đúng");
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


    @Override
    public void onPostExecute(Object o) {

    }
}
